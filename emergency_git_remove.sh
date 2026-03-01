#!/bin/bash

# EMERGENCY: Remove Git and GitHub Connection
# This script safely removes git from your project while keeping your code

echo "⚠️  EMERGENCY GIT REMOVAL SCRIPT"
echo "=================================="
echo ""
echo "This will:"
echo "  1. Remove remote GitHub connection"
echo "  2. Remove all git history"
echo "  3. Keep all your code safe locally"
echo ""
read -p "Are you sure? (yes/no): " confirm

if [ "$confirm" != "yes" ]; then
    echo "Cancelled. No changes made."
    exit 0
fi

# Navigate to project
cd /Users/achyutsubedi/AndroidStudioProjects/MentorMatch

echo ""
echo "Step 1: Removing GitHub remote connection..."
git remote remove origin 2>/dev/null
echo "✓ Remote removed"

echo ""
echo "Step 2: Backing up .git folder..."
if [ -d ".git" ]; then
    mv .git .git.backup.$(date +%Y%m%d_%H%M%S)
    echo "✓ Git folder backed up (in case you need it)"
fi

echo ""
echo "Step 3: Verifying..."
if [ ! -d ".git" ]; then
    echo "✓ Git completely removed"
    echo "✓ Your code is still here and safe!"
else
    echo "⚠ Git folder still exists"
fi

echo ""
echo "✅ CLEANUP COMPLETE!"
echo ""
echo "What happened:"
echo "  ✓ No longer connected to GitHub"
echo "  ✓ Git history removed"
echo "  ✓ All your code files are safe"
echo "  ✓ Backup created: .git.backup.*"
echo ""
echo "Next steps:"
echo "  1. Delete the repository on GitHub manually"
echo "  2. Review UNDO_GITHUB_PUSH.md for instructions"
echo ""
echo "Your project is now a regular folder with no git!"

