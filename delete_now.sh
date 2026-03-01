#!/bin/zsh

echo "🧹 DELETING ALL UNNECESSARY FILES..."
echo "===================================="
echo ""

cd "$(dirname "$0")"

# Delete all .md files except README.md
echo "📄 Deleting documentation files..."
find . -maxdepth 1 -name "*.md" ! -name "README.md" -delete
echo "✅ Documentation files deleted"

# Delete all .sh files except gradlew
echo "📜 Deleting script files..."
find . -maxdepth 1 -name "*.sh" -delete
echo "✅ Script files deleted"

# Delete .txt files
echo "📝 Deleting text files..."
find . -maxdepth 1 -name "*.txt" -delete
echo "✅ Text files deleted"

# Delete Python files
echo "🐍 Deleting Python files..."
find . -maxdepth 1 -name "*.py" -delete
echo "✅ Python files deleted"

# Delete Kotlin script files (except build files)
echo "📋 Deleting Kotlin script files..."
rm -f fetch_agrofarm.kts
echo "✅ Kotlin script files deleted"

# Delete deprecated Navigation.kt
echo "🗂️  Deleting deprecated code files..."
rm -f app/src/main/java/com/example/myapplication/navigation/Navigation.kt
echo "✅ Deprecated Navigation.kt deleted"

# Delete unused DashboardPage.kt
rm -f app/src/main/java/com/example/myapplication/DashboardPage.kt
echo "✅ Unused DashboardPage.kt deleted"

echo ""
echo "✨ CLEANUP COMPLETE!"
echo ""
echo "Files remaining:"
echo "  ✅ README.md"
echo "  ✅ Source code"
echo "  ✅ Build files"
echo "  ✅ Configuration files"
echo ""
echo "🚀 Your project is clean and ready to show!"
echo ""

