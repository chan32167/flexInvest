package one.tinkin.flexInvest.main.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_data.view.*
import one.tinkin.flexInvest.R
import one.tinkin.flexInvest.main.repositories.model.Company

class CompanyAdapter(val items: List<Company>, val context: Context) : RecyclerView.Adapter<ViewHolder>() {

  // Gets the number of animals in the list
  override fun getItemCount(): Int {
    return items.size
  }

  // Inflates the item views
  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
    return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_data, parent, false))
  }

  // Binds each animal in the ArrayList to a view
  override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    holder.image?.setImageURI(items.get(position).image)
    holder.name.setText(items.get(position).name)
    holder.description.setText(items.get(position).description)
  }
}

class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
  // Holds the TextView that will add each animal to
  val image = view.sdv_image
  val name = view.tv_name
  val description = view.tv_description
}