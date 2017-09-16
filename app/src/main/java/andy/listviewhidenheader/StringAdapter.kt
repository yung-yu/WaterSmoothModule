package andy.listviewhidenheader

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

/**
 * Created by andyli on 2017/9/16.
 */

class StringAdapter : RecyclerView.Adapter<StringAdapter.ViewHolder>{
    var context:Context? = null;
    var data:List<String>? = null;

    constructor(context:Context){
        this.context = context;
    }
    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(android.R.layout.simple_list_item_1, null))
    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
          holder?.text?.text = data?.get(position)?:""
    }

    override fun getItemCount(): Int {
        return data?.size?:0;
    }

    inner class ViewHolder: RecyclerView.ViewHolder{
        var text:TextView? = null;
        constructor(itemView: View?) : super(itemView){
            text = itemView?.findViewById(android.R.id.text1);
        }
    }

}