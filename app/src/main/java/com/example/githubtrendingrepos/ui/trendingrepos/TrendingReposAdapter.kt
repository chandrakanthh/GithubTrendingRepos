package com.example.githubtrendingrepos.ui.trendingrepos

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.githubtrendingrepos.R
import com.example.githubtrendingrepos.data.models.TrendingRepoModel
import com.example.githubtrendingrepos.databinding.TrendingRepoItemBinding

class TrendingReposAdapter(private val context: Context, private var list:ArrayList<TrendingRepoModel>) : RecyclerView.Adapter<TrendingReposAdapter.TrendingReposViewHolder>(), Filterable {
    private lateinit var adapterTrendingRepoItemBinding : TrendingRepoItemBinding
    var filterReposData : ArrayList<TrendingRepoModel> = arrayListOf()
    private var selectedListItemPos = -1

    inner class TrendingReposViewHolder(val adapterTrendingRepoItemBinding: TrendingRepoItemBinding) :RecyclerView.ViewHolder(adapterTrendingRepoItemBinding.root) {
        fun bindData(dataListItem: TrendingRepoModel, holder: TrendingReposViewHolder) {
            dataListItem.let {
                adapterTrendingRepoItemBinding.trendingReposItem = it
            }
            adapterTrendingRepoItemBinding.apply {
                authorAvatarIv.load(dataListItem.avatar)
                projectBuildbyIv.load(dataListItem.builtBy[0].avatar)
                val authorName = "${dataListItem.author}/${dataListItem.name}"
                authorNameTv.text = authorName
                projectTodayStarsTv.text = context.getString(R.string.current_stars,dataListItem.currentPeriodStars.toString())
                dataListItem.languageColor?.let {
                    languageColorCv.setCardBackgroundColor(ColorStateList.valueOf(Color.parseColor(it)))
                }

                trendingRepoItemCv.setOnClickListener {
                    if (dataListItem.selectedItemPos != holder.adapterPosition) {

                        trendingRepoItemCv.setCardBackgroundColor(context.resources.getColor(R.color.grey,null))
                        trendingRepoItemCv.cardElevation =
                            context.resources.getDimension(R.dimen.dimens_15)
                        selectedItemListener?.invoke(adapterPosition)
                        selectedListItemPos = holder.adapterPosition
                        dataListItem.selectedItemPos = selectedListItemPos
                        notifyDataSetChanged()
                        //notifyItemChanged(selectedListItemPos)
                    }else{
                        holder.adapterTrendingRepoItemBinding.trendingRepoItemCv.setCardBackgroundColor(context.resources.getColor(R.color.white,null))
                        holder.adapterTrendingRepoItemBinding.trendingRepoItemCv.cardElevation =
                            context.resources.getDimension(R.dimen.dimens_7)
                        selectedListItemPos = -1
                        dataListItem.selectedItemPos = -1
                    }
                    //differ.submitList(differ.currentList)

                }

                if(selectedListItemPos==holder.adapterPosition) {
                    holder.adapterTrendingRepoItemBinding.trendingRepoItemCv.setCardBackgroundColor(context.resources.getColor(R.color.grey,null))
                    holder.adapterTrendingRepoItemBinding.trendingRepoItemCv.cardElevation =
                        context.resources.getDimension(R.dimen.dimens_7)
                }else{
                    holder.adapterTrendingRepoItemBinding.trendingRepoItemCv.setCardBackgroundColor(context.resources.getColor(R.color.white,null))
                    holder.adapterTrendingRepoItemBinding.trendingRepoItemCv.cardElevation =
                        context.resources.getDimension(R.dimen.dimens_7)
                }
            }
        }

    }

    private val differCallback = object : DiffUtil.ItemCallback<TrendingRepoModel>() {
        override fun areItemsTheSame(oldItem: TrendingRepoModel, newItem: TrendingRepoModel): Boolean {
            return if(selectedListItemPos>=0){
                oldItem.selectedItemPos == newItem.selectedItemPos
            }else{
                oldItem.author == newItem.author
            }
        }

        override fun areContentsTheSame(oldItem: TrendingRepoModel, newItem: TrendingRepoModel): Boolean {
            return oldItem == newItem
        }
    }


    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrendingReposAdapter.TrendingReposViewHolder {
        adapterTrendingRepoItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context),
            R.layout.trending_repo_item,parent,false)
        return TrendingReposViewHolder(adapterTrendingRepoItemBinding)
    }

    override fun onBindViewHolder(holder: TrendingReposViewHolder, position: Int) {
        //val dataListItem = list[holder.adapterPosition]
        val dataListItem = differ.currentList[holder.adapterPosition]
        holder.bindData(dataListItem,holder)


    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun updateList(updateRepoList: ArrayList<TrendingRepoModel>, selectedListItemPosition: Int=-1){
        this.list = updateRepoList
        this.selectedListItemPos = selectedListItemPosition
        filterReposData = list
        differ.submitList(updateRepoList)
        //notifyDataSetChanged()
    }

    override fun getFilter(): Filter {
        return object : Filter(){
            override fun performFiltering(p0: CharSequence?): FilterResults {
                val resultList = filterReposData
                val charString = p0.toString()
                val filterdList = ArrayList<TrendingRepoModel>()
                if(charString.isEmpty()){
                    filterdList.addAll(filterReposData)
                }else{
                    for (res in resultList){
                        if(res.name?.lowercase()?.contains(charString.lowercase()) == true || res.author?.lowercase()?.contains(charString.lowercase()) == true){
                            filterdList.add(res)
                        }
                    }
                }
                val filterResults = FilterResults()
                filterResults.values = filterdList
                return filterResults
            }

            override fun publishResults(p0: CharSequence?, p1: FilterResults?) {
                list = p1?.values as ArrayList<TrendingRepoModel>
                differ.submitList(list)
                //notifyDataSetChanged()
            }

        }
    }

    private var selectedItemListener : ((pos: Int) -> Unit)? = null
    fun selectedItemListenerOnClick(listener : (pos: Int) -> Unit){
        this.selectedItemListener = listener
    }
}