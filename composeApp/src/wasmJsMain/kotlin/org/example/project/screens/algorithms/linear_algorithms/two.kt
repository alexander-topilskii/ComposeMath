import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.max
import kotlin.math.min
import kotlin.math.roundToInt
import kotlin.random.Random

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TwoPointersScreenGpt(onBack: () -> Unit) {
    MaterialTheme(colorScheme = darkColorScheme()) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Two Pointers — визуализация и разбор") },
                    navigationIcon = { TextButton(onClick = onBack) { Text("← Назад") } }
                )
            }
        ) { inner ->
            TwoPointersContent(
                Modifier
                    .padding(inner)
                    .fillMaxSize()
            )
        }
    }
}

private enum class DemoAlgo(val title: String) {
    TwoSumSorted("Two Sum (отсортированный массив)"),
    ReverseString("Reverse String"),
    RemoveDuplicates("Удаление дублей (sorted)")
}

private data class StepInfo(
    val message: String,
    val invariant: String,
    val status: String
)

@Composable
private fun TwoPointersContent(modifier: Modifier = Modifier) {
    var algo by remember { mutableStateOf(DemoAlgo.TwoSumSorted) }

    var autoPlay by remember { mutableStateOf(false) }
    var speedMs by remember { mutableStateOf(600) }
    var stepCount by remember { mutableStateOf(0) }

    // TwoSum
    var nums by remember { mutableStateOf(generateSortedArray()) }
    var target by remember { mutableStateOf(24) }
    var i by remember { mutableStateOf(0) }
    var j by remember { mutableStateOf(max(nums.lastIndex, 0)) }
    var foundIndexPair by remember { mutableStateOf<IntPair?>(null) }

    // ReverseString
    var str by remember { mutableStateOf("two pointers") }
    var chars by remember { mutableStateOf(str.toMutableList()) }
    var ri by remember { mutableStateOf(0) }
    var rj by remember { mutableStateOf(max(chars.lastIndex, 0)) }

    // RemoveDuplicates
    var dNums by remember { mutableStateOf(generateSortedArray(allowDuplicates = true)) }
    var slow by remember { mutableStateOf(0) }
    var fast by remember { mutableStateOf(1) }

    val canStep by derivedStateOf {
        when (algo) {
            DemoAlgo.TwoSumSorted -> foundIndexPair == null && i < j
            DemoAlgo.ReverseString -> ri < rj
            DemoAlgo.RemoveDuplicates -> fast < dNums.size
        }
    }

    suspend fun doStep() {
        when (algo) {
            DemoAlgo.TwoSumSorted -> {
                if (i >= j || nums.isEmpty() || foundIndexPair != null) return
                val s = nums[i] + nums[j]
                when {
                    s == target -> foundIndexPair = i to j
                    s < target -> i++
                    else -> j--
                }
            }
            DemoAlgo.ReverseString -> {
                if (ri >= rj || chars.isEmpty()) return
                val tmp = chars[ri]
                chars[ri] = chars[rj]
                chars[rj] = tmp
                ri++; rj--
            }
            DemoAlgo.RemoveDuplicates -> {
                if (fast >= dNums.size) return
                if (dNums[fast] != dNums[slow]) {
                    slow++
                    if (slow != fast) {
                        val list = dNums.toMutableList()
                        list[slow] = dNums[fast]
                        dNums = list
                    }
                }
                fast++
            }
        }
        stepCount++
    }

    LaunchedEffect(autoPlay, algo, speedMs) {
        if (!autoPlay) return@LaunchedEffect
        while (autoPlay && canStep) {
            doStep()
            delay(speedMs.toLong().coerceAtLeast(60L))
        }
        autoPlay = false
    }

    val stepInfo = remember(algo, i, j, foundIndexPair, nums, target, ri, rj, chars, slow, fast, dNums) {
        when (algo) {
            DemoAlgo.TwoSumSorted -> {
                val baseInv = "Инвариант: массив отсортирован; диапазон [i..j] содержит потенциальный ответ."
                val status = when {
                    nums.isEmpty() -> "Пустой массив."
                    foundIndexPair != null -> "Найдено: nums[${foundIndexPair!!.first}] + nums[${foundIndexPair!!.second}] = $target"
                    i >= j -> "Диапазон схлопнулся — пары нет."
                    else -> "Сумма = ${nums[i] + nums[j]}"
                }
                val msg = when {
                    nums.isEmpty() -> "Добавьте элементы."
                    foundIndexPair != null -> "Готово ✔"
                    i >= j -> "Завершение: решений нет."
                    else -> {
                        val s = nums[i] + nums[j]
                        when {
                            s == target -> "Ровно цель → фиксируем индексы."
                            s < target -> "Меньше цели → i++."
                            else -> "Больше цели → j--."
                        }
                    }
                }
                StepInfo(msg, baseInv, status)
            }
            DemoAlgo.ReverseString -> {
                val baseInv = "Инвариант: слева от i и справа от j символы уже на финальных местах."
                val status = if (ri < rj) "Своп: '${chars[ri]}' ↔ '${chars[rj]}'" else "Готово ✔"
                val msg = if (ri < rj) "Меняем местами, затем i++, j--." else "Строка развернута."
                StepInfo(msg, baseInv, status)
            }
            DemoAlgo.RemoveDuplicates -> {
                val baseInv = "Инвариант: [0..slow] — уникальные элементы отсортированного массива."
                val status = if (fast < dNums.size)
                    "Сравниваем dNums[fast]=${dNums[fast]} и dNums[slow]=${dNums[slow]}"
                else "Готово ✔ Длина без дублей = ${min(slow + 1, dNums.size)}"
                val msg = if (fast < dNums.size) {
                    if (dNums[fast] != dNums[slow]) "Новый уникальный → slow++, копируем значение." else "Дубль → fast++."
                } else "Финиш."
                StepInfo(msg, baseInv, status)
            }
        }
    }

    Column(
        modifier = modifier.padding(16.dp).fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                "Two Pointers: два указателя",
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                modifier = Modifier.weight(1f)
            )
            AlgoPicker(algo = algo, onAlgoChange = {
                algo = it
                stepCount = 0
                autoPlay = false
                when (it) {
                    DemoAlgo.TwoSumSorted -> {
                        nums = generateSortedArray()
                        i = 0; j = max(nums.lastIndex, 0)
                        foundIndexPair = null
                        target = listOf(10, 12, 14, 16, 18, 20, 22, 24).random()
                    }
                    DemoAlgo.ReverseString -> {
                        str = "two pointers"
                        chars = str.toMutableList()
                        ri = 0; rj = max(chars.lastIndex, 0)
                    }
                    DemoAlgo.RemoveDuplicates -> {
                        dNums = generateSortedArray(allowDuplicates = true)
                        slow = 0; fast = min(1, dNums.lastIndex)
                    }
                }
            })
        }

        ControlsBar(
            algo = algo,
            nums = nums,
            onRegenerate = {
                stepCount = 0; autoPlay = false
                when (algo) {
                    DemoAlgo.TwoSumSorted -> {
                        nums = generateSortedArray()
                        i = 0; j = max(nums.lastIndex, 0); foundIndexPair = null
                    }
                    DemoAlgo.ReverseString -> {
                        str = randomWord()
                        chars = str.toMutableList()
                        ri = 0; rj = max(chars.lastIndex, 0)
                    }
                    DemoAlgo.RemoveDuplicates -> {
                        dNums = generateSortedArray(allowDuplicates = true)
                        slow = 0; fast = min(1, dNums.lastIndex)
                    }
                }
            },
            target = target,
            onTargetChange = { target = it },
            isPlaying = autoPlay,
            onPlayPause = { autoPlay = !autoPlay },
            onStep = { /* separate button below */ },
            onReset = {
                stepCount = 0; autoPlay = false
                when (algo) {
                    DemoAlgo.TwoSumSorted -> { i = 0; j = max(nums.lastIndex, 0); foundIndexPair = null }
                    DemoAlgo.ReverseString -> { chars = str.toMutableList(); ri = 0; rj = max(chars.lastIndex, 0) }
                    DemoAlgo.RemoveDuplicates -> { slow = 0; fast = min(1, dNums.lastIndex) }
                }
            },
            speedMs = speedMs,
            onSpeedChange = { speedMs = it }
        )

        val scope = rememberCoroutineScope()
        Button(
            onClick = { scope.launch { if (canStep) doStep() } },
            enabled = canStep && !autoPlay
        ) { Text("Шаг") }

        when (algo) {
            DemoAlgo.TwoSumSorted -> {
                ArrayVisualizer(
                    items = nums.map { it.toString() },
                    i = i,
                    j = j,
                    highlightRange = if (i <= j) i..j else null,
                    foundPair = foundIndexPair
                )
            }
            DemoAlgo.ReverseString -> {
                ArrayVisualizer(
                    items = chars.map { it.toString() },
                    i = ri,
                    j = rj,
                    highlightRange = if (ri <= rj) ri..rj else null,
                    foundPair = null
                )
            }
            DemoAlgo.RemoveDuplicates -> {
                ArrayVisualizerDualLeft(
                    items = dNums.map { it.toString() },
                    p1 = slow,
                    p2 = fast,
                    label1 = "slow",
                    label2 = "fast"
                )
                Text("Длина без дублей: ${min(slow + 1, dNums.size)}", style = MaterialTheme.typography.bodyMedium)
            }
        }

        ExplanationPanel(stepInfo, stepCount)
    }
}

@Composable
private fun AlgoPicker(algo: DemoAlgo, onAlgoChange: (DemoAlgo) -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    Box {
        OutlinedButton(onClick = { expanded = true }) { Text(algo.title) }
        androidx.compose.material3.DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            DemoAlgo.values().forEach {
                androidx.compose.material3.DropdownMenuItem(
                    text = { Text(it.title) },
                    onClick = { expanded = false; onAlgoChange(it) }
                )
            }
        }
    }
}

@Composable
private fun ControlsBar(
    algo: DemoAlgo,
    nums: List<Int>,
    onRegenerate: () -> Unit,
    target: Int,
    onTargetChange: (Int) -> Unit,
    isPlaying: Boolean,
    onPlayPause: () -> Unit,
    onStep: () -> Unit,
    onReset: () -> Unit,
    speedMs: Int,
    onSpeedChange: (Int) -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(onClick = onRegenerate) { Text("Сгенерировать") }
            when (algo) {
                DemoAlgo.TwoSumSorted -> {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text("Цель:", modifier = Modifier.padding(end = 6.dp))
                        IntegerField(value = target, onChange = onTargetChange, width = 90.dp)
                    }
                    Text(
                        "Массив отсортирован ↑: ${nums.joinToString()}",
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.weight(1f),
                        textAlign = TextAlign.End
                    )
                }
                else -> Spacer(Modifier.weight(1f))
            }
        }
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            FilledTonalButton(onClick = onPlayPause) { Text(if (isPlaying) "Пауза" else "Авто ▶") }
            OutlinedButton(onClick = onStep) { Text("Шаг") }
            OutlinedButton(onClick = onReset) { Text("Сброс") }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("Скорость:", modifier = Modifier.padding(start = 8.dp, end = 8.dp))
                Slider(
                    value = speedMs.toFloat(),
                    valueRange = 120f..1500f,
                    onValueChange = { onSpeedChange(it.roundToInt()) },
                    modifier = Modifier.width(220.dp)
                )
                Text("${speedMs}мс/шаг", style = MaterialTheme.typography.labelSmall)
            }
        }
    }
}

@Composable
private fun ArrayVisualizer(
    items: List<String>,
    i: Int,
    j: Int,
    highlightRange: IntRange?,
    foundPair: IntPair?
) {
    val cellW = 56.dp
    val cellH = 56.dp
    val spacing = 8.dp
    val scroll = rememberScrollState()
    val density = LocalDensity.current

    val totalWidthDp = remember(items.size, cellW, spacing) {
        with(density) {
            ((items.size * cellW.value) + (items.size - 1) * spacing.value).dp
        }
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .horizontalScroll(scroll)
    ) {
        // Row of cells
        Box(modifier = Modifier.padding(top = 36.dp)) {
            Row(horizontalArrangement = Arrangement.spacedBy(spacing)) {
                items.forEachIndexed { idx, value ->
                    val isInRange = highlightRange?.contains(idx) == true
                    val isFound = foundPair?.let { idx == it.first || idx == it.second } == true
                    val bg = when {
                        isFound -> Color(0xFF2E7D32)
                        isInRange -> Color(0xFF283593)
                        else -> Color(0xFF263238)
                    }
                    CellBox(value = value, w = cellW, h = cellH, bg = bg)
                }
            }
        }
        // Pointers and labels
        PointerOverlayWithLabels(
            count = items.size,
            i = i,
            j = j,
            cellWidth = cellW,
            spacing = spacing,
            totalWidth = totalWidthDp
        )
    }
}

@Composable
private fun ArrayVisualizerDualLeft(
    items: List<String>,
    p1: Int,
    p2: Int,
    label1: String,
    label2: String
) {
    val cellW = 56.dp
    val cellH = 56.dp
    val spacing = 8.dp
    val scroll = rememberScrollState()
    val density = LocalDensity.current

    val totalWidthDp = remember(items.size, cellW, spacing) {
        with(density) {
            ((items.size * cellW.value) + (items.size - 1) * spacing.value).dp
        }
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .horizontalScroll(scroll)
    ) {
        Box(modifier = Modifier.padding(top = 36.dp)) {
            Row(horizontalArrangement = Arrangement.spacedBy(spacing)) {
                items.forEach { value ->
                    CellBox(value = value, w = cellW, h = cellH, bg = Color(0xFF263238))
                }
            }
        }
        PointerOverlayTwoLeftWithLabels(
            count = items.size,
            p1 = p1,
            p2 = p2,
            cellWidth = cellW,
            spacing = spacing,
            totalWidth = totalWidthDp,
            label1 = label1,
            label2 = label2
        )
    }
}

@Composable
private fun CellBox(value: String, w: Dp, h: Dp, bg: Color) {
    Box(
        modifier = Modifier
            .size(w, h)
            .clip(RoundedCornerShape(12.dp))
            .background(bg)
            .border(1.dp, Color(0xFF455A64), RoundedCornerShape(12.dp)),
        contentAlignment = Alignment.Center
    ) {
        Text(value, fontSize = 18.sp, fontWeight = FontWeight.Medium)
    }
}

/**
 * Multiplatform-safe pointer overlay: draws ONLY shapes on Canvas,
 * and places Text labels using composables (no drawText / android Paint).
 */
@Composable
private fun PointerOverlayWithLabels(
    count: Int,
    i: Int,
    j: Int,
    cellWidth: Dp,
    spacing: Dp,
    totalWidth: Dp
) {
    val density = LocalDensity.current
    val cellPx = with(density) { cellWidth.toPx() }
    val spacePx = with(density) { spacing.toPx() }

    val animI by animateFloatAsState(i.toFloat(), animationSpec = tween(250, easing = LinearEasing))
    val animJ by animateFloatAsState(j.toFloat(), animationSpec = tween(250, easing = LinearEasing))

    // Canvas for arrows
    Box(
        modifier = Modifier
            .width(totalWidth)
            .height(36.dp + 56.dp)
    ) {
        Canvas(modifier = Modifier.matchParentSize()) {
            fun centerX(index: Float) = index * (cellPx + spacePx) + cellPx / 2f
            fun drawPointerX(index: Float, up: Boolean, color: Color) {
                val cx = centerX(index)
                val arrowHeight = 22f
                val baseY = if (up) 28f else size.height - 6f
                val tipY = if (up) baseY - arrowHeight else baseY + arrowHeight
                val path = Path().apply {
                    moveTo(cx - 10f, baseY)
                    lineTo(cx + 10f, baseY)
                    lineTo(cx, tipY)
                    close()
                }
                drawPath(path, color)
            }
            if (count > 0 && i in 0 until count) drawPointerX(animI, true, Color(0xFFFFC107))
            if (count > 0 && j in 0 until count) drawPointerX(animJ, false, Color(0xFFFF5252))
        }

        // Labels as regular composables (no Canvas text APIs)
        val iX = remember(animI, cellPx, spacePx) { animI * (cellPx + spacePx) + cellPx / 2f }
        val jX = remember(animJ, cellPx, spacePx) { animJ * (cellPx + spacePx) + cellPx / 2f }

        if (count > 0 && i in 0 until count) {
            Text(
                "i",
                fontSize = 14.sp,
                modifier = Modifier.offset {
                    IntOffset(x = (iX - 6f).roundToInt(), y = 2)
                }
            )
        }
        if (count > 0 && j in 0 until count) {
            Text(
                "j",
                fontSize = 14.sp,
                modifier = Modifier.offset {
                    // Near bottom
                    IntOffset(
                        x = (jX - 6f).roundToInt(),
                        y = with(density) { (36.dp + 56.dp - 18.dp).roundToPx() }
                    )
                }
            )
        }
    }
}

@Composable
private fun PointerOverlayTwoLeftWithLabels(
    count: Int,
    p1: Int,
    p2: Int,
    cellWidth: Dp,
    spacing: Dp,
    totalWidth: Dp,
    label1: String,
    label2: String
) {
    val density = LocalDensity.current
    val cellPx = with(density) { cellWidth.toPx() }
    val spacePx = with(density) { spacing.toPx() }

    val anim1 by animateFloatAsState(p1.toFloat(), animationSpec = tween(250, easing = LinearEasing))
    val anim2 by animateFloatAsState(p2.toFloat(), animationSpec = tween(250, easing = LinearEasing))

    Box(
        modifier = Modifier
            .width(totalWidth)
            .height(36.dp + 56.dp + 28.dp)
    ) {
        Canvas(modifier = Modifier.matchParentSize()) {
            fun centerX(index: Float) = index * (cellPx + spacePx) + cellPx / 2f
            fun drawPointerDown(index: Float, color: Color, yOffset: Float) {
                val cx = centerX(index)
                val baseY = size.height - yOffset
                val arrowHeight = 22f
                val tipY = baseY + arrowHeight
                val path = Path().apply {
                    moveTo(cx - 10f, baseY)
                    lineTo(cx + 10f, baseY)
                    lineTo(cx, tipY)
                    close()
                }
                drawPath(path, color)
            }
            if (count > 0 && p1 in 0 until count) drawPointerDown(anim1, Color(0xFFFFC107), 24f)
            if (count > 0 && p2 in 0 until count) drawPointerDown(anim2, Color(0xFF80DEEA), 2f)
        }

        val x1 = remember(anim1, cellPx, spacePx) { anim1 * (cellPx + spacePx) + cellPx / 2f }
        val x2 = remember(anim2, cellPx, spacePx) { anim2 * (cellPx + spacePx) + cellPx / 2f }

        if (count > 0 && p1 in 0 until count) {
            Text(
                label1,
                fontSize = 14.sp,
                modifier = Modifier.offset {
                    IntOffset(
                        x = (x1 - 12f).roundToInt(),
                        y = with(density) { (36.dp + 56.dp - 10.dp).roundToPx() }
                    )
                }
            )
        }
        if (count > 0 && p2 in 0 until count) {
            Text(
                label2,
                fontSize = 14.sp,
                modifier = Modifier.offset {
                    IntOffset(
                        x = (x2 - 12f).roundToInt(),
                        y = with(density) { (36.dp + 56.dp + 10.dp).roundToPx() }
                    )
                }
            )
        }
    }
}

@Composable
private fun ExplanationPanel(info: StepInfo, stepCount: Int) {
    ElevatedCard(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text("Шагов: $stepCount", style = MaterialTheme.typography.labelLarge)
            Text("Статус: ${info.status}", fontWeight = FontWeight.Medium)
            Text("Что делаем дальше: ${info.message}")
            Divider(Modifier.padding(vertical = 6.dp))
            Text(
                "Почему это работает:\n• ${info.invariant}\n• Двигая указатели, сокращаем пространство поиска, не нарушая инварианта.",
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}

@Composable
private fun IntegerField(value: Int, onChange: (Int) -> Unit, width: Dp) {
    var text by remember(value) { mutableStateOf(value.toString()) }
    OutlinedTextField(
        value = text,
        onValueChange = {
            val filtered = it.filter { ch -> ch == '-' || ch.isDigit() }.take(6)
            text = filtered
            filtered.toIntOrNull()?.let(onChange)
        },
        singleLine = true,
        modifier = Modifier.width(width),
        textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.End)
    )
}

private typealias IntPair = Pair<Int, Int>

private fun generateSortedArray(
    size: Int = Random.nextInt(8, 14),
    minVal: Int = 1,
    maxVal: Int = 30,
    allowDuplicates: Boolean = false
): List<Int> {
    val base = MutableList(size) {
        if (allowDuplicates) Random.nextInt(minVal, maxVal)
        else minVal + it * Random.nextInt(1, 4)
    }.sorted()
    return if (allowDuplicates) base.sorted() else base.distinct().sorted()
}

private fun randomWord(): String {
    val pool = listOf(
        "two pointers", "abracadabra", "kotlin compose", "algorithms",
        "racecar level", "datastruct", "invariant", "monotonic"
    )
    return pool.random()
}
