import 'package:flutter/services.dart';
import 'package:flutter_test/flutter_test.dart';
import 'package:sdk_smarthouse/sdk_smarthouse.dart';

void main() {
  const MethodChannel channel = MethodChannel('sdk_smarthouse');

  TestWidgetsFlutterBinding.ensureInitialized();

  setUp(() {
    channel.setMockMethodCallHandler((MethodCall methodCall) async {
      return '42';
    });
  });

  tearDown(() {
    channel.setMockMethodCallHandler(null);
  });

  test('getPlatformVersion', () async {
    expect(await SdkSmarthouse.platformVersion, '42');
  });
}
