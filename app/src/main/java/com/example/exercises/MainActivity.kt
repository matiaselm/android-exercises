package com.example.exercises

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    val wikiApiServe by lazy {
        WikiApiService.create()
    }

    var disposable: Disposable? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //  This creates an array of presidents that’ll contain the text to be displayed in the ListView.
        val presidentList = GlobalModel.presidents.toTypedArray()

        val adapter = ListAdapter(this, presidentList)
        presidentListView.adapter = adapter

        presidentListView.setOnItemClickListener { _, _, position, _ ->
            Log.d("list-lab", "selected $position")
            presidentNameView.text = presidentList[position].name
            presidentDescriptionView.text = presidentList[position].description

            GlobalScope.launch {
                beginSearch(presidentList[position].name)
            }

        }

        presidentListView.setOnItemLongClickListener { _, _, position, _ ->
            Log.d("list-lab","long click on $position")

            true
        }
    }

    private fun beginSearch(srsearch: String) {
        disposable = wikiApiServe.hitCountCheck("query", "json", "search", srsearch)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { result -> showResult(result.query.searchinfo.totalhits) },
                { error -> showError(error.message) }
            )
    }

    private fun showResult(result: Int) {
        hitsTextView.text = "Hits:"
        presidentHits.text = result.toString()
    }

    private fun showError(error: String?) {
        if (error != null) {
            Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "some error happened", Toast.LENGTH_SHORT).show()
        }
    }

}

