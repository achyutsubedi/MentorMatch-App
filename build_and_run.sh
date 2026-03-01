#!/bin/zsh

# Quick Build Script for MentorMatch
# This script builds and runs the MentorMatch app

echo "🚀 MentorMatch - Quick Build & Run Script"
echo "=========================================="
echo ""

# Navigate to project directory
cd "$(dirname "$0")"

# Step 1: Clean
echo "📦 Step 1/3: Cleaning previous builds..."
./gradlew clean

if [ $? -ne 0 ]; then
    echo "❌ Clean failed! Please check the error above."
    exit 1
fi

echo "✅ Clean successful!"
echo ""

# Step 2: Build
echo "🔨 Step 2/3: Building the project..."
./gradlew build

if [ $? -ne 0 ]; then
    echo "❌ Build failed! Please check the error above."
    exit 1
fi

echo "✅ Build successful!"
echo ""

# Step 3: Install on device
echo "📱 Step 3/3: Installing on device/emulator..."
./gradlew installDebug

if [ $? -ne 0 ]; then
    echo "❌ Installation failed! Make sure a device/emulator is connected."
    echo ""
    echo "To check connected devices, run: adb devices"
    exit 1
fi

echo "✅ Installation successful!"
echo ""
echo "🎉 MentorMatch is now installed on your device!"
echo ""
echo "Next steps:"
echo "  • Open the app on your device/emulator"
echo "  • Register a new account or login"
echo "  • Enjoy using MentorMatch!"
echo ""

