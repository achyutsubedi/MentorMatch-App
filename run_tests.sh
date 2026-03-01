#!/bin/zsh

# Test Runner for MentorMatch
# Runs all unit tests and instrumented tests

echo "🧪 MentorMatch - Test Runner"
echo "============================"
echo ""

# Navigate to project directory
cd "$(dirname "$0")"

# Run Unit Tests
echo "📝 Running Unit Tests..."
./gradlew test

if [ $? -ne 0 ]; then
    echo "❌ Unit tests failed!"
    exit 1
fi

echo "✅ Unit tests passed!"
echo ""

# Ask about instrumented tests
echo "📱 Do you want to run instrumented tests? (requires connected device/emulator)"
echo "Make sure a device or emulator is connected."
echo ""
read -p "Run instrumented tests? (y/n): " -n 1 -r
echo ""

if [[ $REPLY =~ ^[Yy]$ ]]; then
    echo "🔌 Checking for connected devices..."
    adb devices
    echo ""

    echo "🧪 Running instrumented tests..."
    ./gradlew connectedAndroidTest

    if [ $? -ne 0 ]; then
        echo "❌ Instrumented tests failed!"
        exit 1
    fi

    echo "✅ Instrumented tests passed!"
fi

echo ""
echo "🎉 All requested tests completed successfully!"
echo ""

