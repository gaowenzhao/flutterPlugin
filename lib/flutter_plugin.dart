import 'dart:async';

import 'package:flutter/services.dart';

class FlutterPlugin {
  static const MethodChannel _channel = const MethodChannel('com.zhao.flutterplugin/test');

  static Future<String> get platformVersion async {
    final String version = await _channel.invokeMethod('getPlatformVersion');
    return version;
  }

  static Future<String> get battery async {
    final String version = await _channel.invokeMethod('getBattery');
    return version;
  }
}
