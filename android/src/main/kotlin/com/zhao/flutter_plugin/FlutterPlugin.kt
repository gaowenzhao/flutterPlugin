package com.zhao.flutter_plugin

import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.content.IntentFilter
import android.os.BatteryManager
import android.os.Build
import android.os.Build.VERSION.SDK_INT
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler
import io.flutter.plugin.common.MethodChannel.Result
import io.flutter.plugin.common.PluginRegistry.Registrar

class FlutterPlugin: MethodCallHandler {
  companion object {
    lateinit var register:Registrar
    @JvmStatic
    fun registerWith(registrar: Registrar) {
      this.register = registrar
      val channel = MethodChannel(registrar.messenger(), "com.zhao.flutterplugin/test")
      channel.setMethodCallHandler(FlutterPlugin())
    }
  }

  override fun onMethodCall(call: MethodCall, result: Result) {
    when(call.method){
      "getPlatformVersion"->{
        result.success("Android getPlatformVersion=${Build.VERSION.RELEASE}")
      }
      "getBattery"->{
        result.success("Android getBattery=${getBatteryLevel()}")
      }
      else->{
        result.notImplemented()
      }
    }
  }
  private fun getBatteryLevel(): Int {
    val batteryLevel: Int
    batteryLevel = if (SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
      val batteryManager = register.context().getSystemService(Context.BATTERY_SERVICE) as BatteryManager
      batteryManager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY)
    } else {
      val intent = ContextWrapper(register.context()).registerReceiver(null, IntentFilter(Intent.ACTION_BATTERY_CHANGED))
      intent!!.getIntExtra(BatteryManager.EXTRA_LEVEL, -1) * 100 / intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1)
    }
    return batteryLevel
  }
}
