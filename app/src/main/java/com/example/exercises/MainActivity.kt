package com.example.exercises

import android.Manifest
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.bluetooth.le.ScanCallback
import android.bluetooth.le.ScanFilter
import android.bluetooth.le.ScanResult
import android.bluetooth.le.ScanSettings
import android.content.Context
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    companion object {
        const val SCAN_PERIOD: Long = 3000
        private var mBluetoothAdapter: BluetoothAdapter? = null
        private var mScanResults: HashMap<String, ScanResult>? = null
        private var mScanCallback: BtleScanCallback? = null
        private var mScanning: Boolean = false
    }


    private fun hasPermissions(): Boolean {
        if (mBluetoothAdapter == null || !mBluetoothAdapter!!.isEnabled) {
            Log.d("bluetooth-lab", "No bluetooth LE capability")
            return false
        } else if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Log.d("bluetooth-lab", "No fine location access")
            requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 1);
            return true // Assuming the user grants permission
        }

        return true
    }

    private inner class BtleScanCallback : ScanCallback() {
        override fun onScanResult(callbackType: Int, result: ScanResult) {
            addScanResult(result)
        }

        override fun onBatchScanResults(results: MutableList<ScanResult>?) {
            if (results != null) {
                for (result in results) {
                    addScanResult(result)
                }
            } else {
                return
            }
        }

        override fun onScanFailed(errorCode: Int) {
            Log.d("bluetooth-lab", "BLE Scan failed with code $errorCode")
        }

        private fun addScanResult(result: ScanResult) {
            val device = result.device
            val deviceAddress = device.address
            mScanResults!![deviceAddress] = result

            Log.d("bluetooth-lab", "Device address: $deviceAddress (${result.isConnectable}")
        }
    }


    // Starts scan and finds devices, works at least somewhat
    private fun startScan() {
        Log.d("bluetooth-lab", "Scan start")
        mScanResults = HashMap()
        mScanCallback = BtleScanCallback()
        val mBluetoothLeScanner = mBluetoothAdapter!!.bluetoothLeScanner

        val settings = ScanSettings.Builder()
            .setScanMode(ScanSettings.SCAN_MODE_LOW_POWER)
            .build()

        val filter: List<ScanFilter>? = null

        // Stops scanning after a pre-defined scan period
        val mHandler = Handler()
        mHandler.postDelayed({ stopScan() }, SCAN_PERIOD)

        mScanning = true
        mBluetoothLeScanner!!.startScan(filter, settings, mScanCallback)
    }

    // Isn't working as supposed to, doesn't stop the scan
    private fun stopScan() {
        Log.d("bluetooth-lab","Scan stop")
        val mBluetoothLeScanner = mBluetoothAdapter!!.bluetoothLeScanner
        mScanCallback = BtleScanCallback()
        mScanning = false
        mBluetoothLeScanner!!.stopScan(mScanCallback)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bluetoothManager = getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
        mBluetoothAdapter = bluetoothManager.adapter

        button.setOnClickListener() {
            if(hasPermissions()){
                startScan()
            }
        }
    }
}

