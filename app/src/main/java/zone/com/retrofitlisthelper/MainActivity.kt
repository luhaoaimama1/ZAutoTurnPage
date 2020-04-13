package zone.com.retrofitlisthelper

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity(), View.OnClickListener {
    var llRoot: LinearLayout? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        llRoot = findViewById(R.id.ll_root)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.bt_List -> onListClick(true)
            R.id.bt_List_no -> onListClick(false)
        }
    }

    fun onListClick(hasLoadingLayout: Boolean) {
        val intent = Intent(this, ListActivity::class.java)
        intent.putExtra("isLoadingLayout", hasLoadingLayout)
        startActivity(intent)
    }
}