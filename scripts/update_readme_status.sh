#!/bin/bash
# Update deployment status and timestamp in README
# Usage: ./scripts/update_readme_status.sh <status>
set -euo pipefail
STATUS="${1:-unknown}"
TIMESTAMP=$(date -u '+%Y-%m-%d %H:%M UTC')
# Replace lines in README
sed -i -E "s#\*\*Deployment status:\*\*.*#**Deployment status:** ${STATUS}#" README.md
sed -i -E "s#\*\*Last updated:\*\*.*#**Last updated:** ${TIMESTAMP}#" README.md
