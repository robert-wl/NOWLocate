package edu.bluejack23_1.nowlocate.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import edu.bluejack23_1.nowlocate.R
import edu.bluejack23_1.nowlocate.models.CategoryType
import edu.bluejack23_1.nowlocate.models.Filter
import edu.bluejack23_1.nowlocate.viewModels.HomeViewModel
import edu.bluejack23_1.nowlocate.views.viewHolders.FilterViewHolder

class FilterAdapter(private var context: Context, private var viewModel: HomeViewModel) :
    RecyclerView.Adapter<FilterViewHolder>() {
    lateinit var filterList: ArrayList<Filter>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilterViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.filter_card, parent, false)
        return FilterViewHolder(view)
    }

    override fun getItemCount(): Int {
        return filterList.size
    }

    override fun onBindViewHolder(holder: FilterViewHolder, position: Int) {
        holder.filterNameTV.text = filterList[position].filterName
        holder.filterAmountTV.text =
            context.getString(R.string.filter_items, filterList[position].filterItemCount)
        holder.filterIconIV.setImageResource(getFilterIcon(filterList[position].filterCategoryType))

        holder.filterCardCV.setOnClickListener {
            viewModel.filterQuery.value = filterList[position].filterName
        }
    }

    fun getFilterIcon(type: CategoryType): Int {
        return when (type) {
            CategoryType.ELECTRONICS -> R.drawable.baseline_phonelink_24
            CategoryType.PERSONAL_ITEMS -> R.drawable.baseline_person_24
            CategoryType.KEYS -> R.drawable.baseline_vpn_key_24
            CategoryType.WALLETS_AND_PURSES -> R.drawable.baseline_wallet_24
            CategoryType.DOCUMENTS -> R.drawable.baseline_newspaper_24
            CategoryType.JEWELRY -> R.drawable.baseline_diamond_24
            CategoryType.BAGS_AND_LUGGAGE -> R.drawable.baseline_shopping_bag_24
            CategoryType.BOOKS -> R.drawable.baseline_book_24
            CategoryType.GADGETS -> R.drawable.baseline_phone_iphone_24
            CategoryType.TOYS -> R.drawable.baseline_smart_toy_24
        }
    }
}