package com.myozawoo.sample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.widget.addTextChangedListener
import com.jakewharton.rxbinding3.widget.textChanges
import com.myozawoo.mmkyat_converter.MyanmarKyatConverter
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val compositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        add(edNumber.textChanges().subscribe {
            tvOutput.text = MyanmarKyatConverter.convertToMyanmarKyat(it)
        })
    }

    fun add(disposable: Disposable) {
        compositeDisposable.add(disposable)
    }
    override fun onDestroy() {
        super.onDestroy()
        if (!compositeDisposable.isDisposed) compositeDisposable.clear()
    }

}
