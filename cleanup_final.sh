#!/bin/zsh

# Cleanup Script for MentorMatch Project
# This script removes all unnecessary documentation and temporary files

echo "🧹 Starting cleanup of MentorMatch project..."

# Navigate to project root
cd "$(dirname "$0")"

# List of files to delete (keep only essential files)
FILES_TO_DELETE=(
    "ACTION_REQUIRED.md"
    "ALL_ERRORS_FIXED_FINAL_STATUS.md"
    "ALL_ERRORS_FIXED_FINAL.md"
    "ALL_ERRORS_FIXED_READY_TO_RUN.md"
    "ALL_ERRORS_FIXED.md"
    "ANALYZE_AGROFARM.md"
    "analyze_agrofarm.sh"
    "BUILD_ERROR_FIXED_NOW.md"
    "BUILD_ERROR_FIXED.md"
    "build_fix.sh"
    "BUILD_GRADLE_FIXED.md"
    "CHECKLIST.md"
    "CLEANUP_COMPLETE.sh"
    "cleanup_now.sh"
    "CLEANUP_PROJECT.sh"
    "CLEANUP_SUMMARY.md"
    "COMPLETE_VERIFICATION_REPORT.md"
    "CURRENT_STATE_BACKUP.md"
    "DARK_MODE_BUTTON_LOCATION.md"
    "DARK_MODE_IMPLEMENTATION_GUIDE.md"
    "DELETE_LIST.md"
    "DELETE_THIS_FILE.txt"
    "DO_CLEANUP_NOW.sh"
    "EMAIL_SERVICE_FIXED.md"
    "emergency_git_remove.sh"
    "fetch_agrofarm.kts"
    "fetch_agrofarm.py"
    "FINAL_COMPLETE_VERIFICATION.md"
    "FINAL_ERROR_FIX_VERIFICATION.md"
    "FINAL_INSTRUCTIONS.md"
    "FINAL_SUMMARY.md"
    "FIX_COMPLETE.md"
    "GITHUB_PUSH_GUIDE.md"
    "IMAGE_UPLOAD_GUIDE.md"
    "IMAGE_UPLOAD_QUICK_START.md"
    "IMPLEMENTATION_COMPLETE.md"
    "IMPLEMENTATION_GUIDE.md"
    "INFORMATION_NEEDED.md"
    "LOGIN_REGISTER_OTP_COMPLETE.md"
    "MANUAL_CLEANUP_GUIDE.md"
    "MENTOR_MATCH_IMPLEMENTATION.md"
    "MENTORMATCH_README.md"
    "NAVIGATION_ERRORS_FIXED.md"
    "NAVIGATION_FLOW_UPDATED.md"
    "NO_ERRORS_FOUND.md"
    "OTP_EMAIL_COMPLETE.md"
    "OTP_EMAIL_SETUP_GUIDE.md"
    "OTP_FEATURE_REMOVED.md"
    "OTP_REMOVAL_SUMMARY.md"
    "PROJECT_CLEANUP_SUMMARY.md"
    "PROJECT_SUMMARY.md"
    "QUICK_PUSH_COMMANDS.md"
    "QUICK_START.md"
    "README_GITHUB.md"
    "README_NEW.md"
    "README_TRANSFORMATION.md"
    "RUN_APP_NOW.md"
    "SETTINGS_IMPLEMENTATION_SUMMARY.md"
    "SETTINGS_PAGE_COMPLETE.md"
    "SETTINGS_QUICK_START.md"
    "SPLASH_SCREEN_FIX.md"
    "START_HERE.md"
    "TEST_ERRORS_FIXED.md"
    "TEST_QUICK_REFERENCE.md"
    "TESTING_GUIDE.md"
    "THEME_ERRORS_FIXED.md"
    "transform_step1_analyze.sh"
    "TRANSFORMATION_PLAN.md"
    "UNDO_GITHUB_PUSH.md"
    "WHERE_TO_UPLOAD_IMAGES.md"
    "YOUTUBE_VIDEO_IMPLEMENTATION.md"
)

# Delete each file if it exists
for file in "${FILES_TO_DELETE[@]}"; do
    if [ -f "$file" ]; then
        rm "$file"
        echo "✅ Deleted: $file"
    fi
done

# Delete deprecated Navigation.kt file
DEPRECATED_NAV="app/src/main/java/com/example/myapplication/navigation/Navigation.kt"
if [ -f "$DEPRECATED_NAV" ]; then
    rm "$DEPRECATED_NAV"
    echo "✅ Deleted: $DEPRECATED_NAV (deprecated)"
fi

# Delete unused DashboardPage.kt file
UNUSED_DASHBOARD="app/src/main/java/com/example/myapplication/DashboardPage.kt"
if [ -f "$UNUSED_DASHBOARD" ]; then
    rm "$UNUSED_DASHBOARD"
    echo "✅ Deleted: $UNUSED_DASHBOARD (unused)"
fi

echo ""
echo "✨ Cleanup complete!"
echo ""
echo "Files kept:"
echo "  - README.md (project documentation)"
echo "  - ERRORS_FIXED.md (recent fixes)"
echo "  - All source code files"
echo "  - Build configuration files"
echo ""
echo "Next steps:"
echo "  1. Run: ./gradlew clean"
echo "  2. Run: ./gradlew build"
echo "  3. Test the app"
echo ""

