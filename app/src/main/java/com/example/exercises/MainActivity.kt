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
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    companion object {
        const val SCAN_PERIOD: Long = 3000
        private var mBluetoothAdapter: BluetoothAdapter? = null
        private var mScanResults: HashMap<String, ScanResult>? = null
        private var mScanCallback: BtleScanCallback? = null
        private var mScanning: Boolean = false
    }

    private lateinit var adapter: RecyclerAdapter
    private lateinit var deviceList: ArrayList<Device>
    private lateinit var nameList: ArrayList<String>

    private fun hasPermissions(): Boolean {
        return if (mBluetoothAdapter == null || !mBluetoothAdapter!!.isEnabled) {
            Log.d("bluetooth-lab", "No bluetooth LE capability")
            false
        } else if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Log.d("bluetooth-lab", "No fine location access")
            requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 1);
            true // Assuming the user grants permission
        } else {
            true
        }
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

            val newDevice = Device(device.name,device.address,result.isConnectable.toString())
            val deviceName = "${newDevice.name} ${device.address}"
            if(!nameList.contains(deviceName)){
                nameList.add(deviceName)
                deviceList.add(newDevice)
                adapter.notifyDataSetChanged()
            }

            Log.d("bluetooth-lab","$deviceList")

            Log.d("bluetooth-lab", "Device: ${device.name} $deviceAddress (${result.isConnectable}")
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
        mHandler.postDelayed({ mBluetoothLeScanner!!.stopScan(mScanCallback) }, SCAN_PERIOD)

        mScanning = true
        mBluetoothLeScanner!!.startScan(filter, settings, mScanCallback)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        deviceList = ArrayList()
        nameList = ArrayList()
        adapter = RecyclerAdapter(this,deviceList)

        // Recycler
        val rv = recyclerView as RecyclerView
        rv.layoutManager = LinearLayoutManager(this)
        rv.itemAnimator = DefaultItemAnimator()

        // Set adapter
        rv.adapter = adapter

        val bluetoothManager = getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
        mBluetoothAdapter = bluetoothManager.adapter

        buttonStart.setOnClickListener() {
            if (hasPermissions()) {
                startScan()
            }
        }
    }
}

