#!/usr/bin/env python3
import os
import glob

print("🧹 CLEANING UP MENTORMATCH PROJECT...")
print("=" * 50)
print()

# Get the directory where the script is located
script_dir = os.path.dirname(os.path.abspath(__file__))
os.chdir(script_dir)

# Files to delete
files_deleted = 0

# Delete all .md files except README.md
print("📄 Deleting documentation files...")
for md_file in glob.glob("*.md"):
    if md_file != "README.md":
        try:
            os.remove(md_file)
            print(f"  ✅ Deleted: {md_file}")
            files_deleted += 1
        except Exception as e:
            print(f"  ❌ Failed to delete {md_file}: {e}")

# Delete all .sh files
print("\n📜 Deleting script files...")
for sh_file in glob.glob("*.sh"):
    try:
        os.remove(sh_file)
        print(f"  ✅ Deleted: {sh_file}")
        files_deleted += 1
    except Exception as e:
        print(f"  ❌ Failed to delete {sh_file}: {e}")

# Delete .txt files
print("\n📝 Deleting text files...")
for txt_file in glob.glob("*.txt"):
    try:
        os.remove(txt_file)
        print(f"  ✅ Deleted: {txt_file}")
        files_deleted += 1
    except Exception as e:
        print(f"  ❌ Failed to delete {txt_file}: {e}")

# Delete Python files except this one
print("\n🐍 Deleting Python files...")
for py_file in glob.glob("*.py"):
    if py_file != os.path.basename(__file__):
        try:
            os.remove(py_file)
            print(f"  ✅ Deleted: {py_file}")
            files_deleted += 1
        except Exception as e:
            print(f"  ❌ Failed to delete {py_file}: {e}")

# Delete Kotlin script files
print("\n📋 Deleting Kotlin script files...")
if os.path.exists("fetch_agrofarm.kts"):
    try:
        os.remove("fetch_agrofarm.kts")
        print(f"  ✅ Deleted: fetch_agrofarm.kts")
        files_deleted += 1
    except Exception as e:
        print(f"  ❌ Failed to delete fetch_agrofarm.kts: {e}")

# Delete deprecated Navigation.kt
print("\n🗂️  Deleting deprecated code files...")
nav_file = "app/src/main/java/com/example/myapplication/navigation/Navigation.kt"
if os.path.exists(nav_file):
    try:
        os.remove(nav_file)
        print(f"  ✅ Deleted: Navigation.kt (deprecated)")
        files_deleted += 1
    except Exception as e:
        print(f"  ❌ Failed to delete Navigation.kt: {e}")

# Delete unused DashboardPage.kt
dashboard_file = "app/src/main/java/com/example/myapplication/DashboardPage.kt"
if os.path.exists(dashboard_file):
    try:
        os.remove(dashboard_file)
        print(f"  ✅ Deleted: DashboardPage.kt (unused)")
        files_deleted += 1
    except Exception as e:
        print(f"  ❌ Failed to delete DashboardPage.kt: {e}")

print()
print("=" * 50)
print(f"✨ CLEANUP COMPLETE! Deleted {files_deleted} files")
print()
print("Files remaining:")
print("  ✅ README.md (project documentation)")
print("  ✅ All source code files")
print("  ✅ Build configuration files")
print("  ✅ Firebase configuration")
print()
print("🚀 Your project is clean and ready to show to your teacher!")
print()
print("Next step: Run './gradlew clean build' to build the app")
print()

