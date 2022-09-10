package com.sample.vkoelassign.ui.view.adapter

import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sample.vkoelassign.databinding.ItemMovieBinding
import com.sample.vkoelassign.ui.model.Result
import com.sample.vkoelassign.utility.Constants
import com.sample.vkoelassign.utility.Utils

class MovieAdapter(val movieList: ArrayList<Result>) :
    RecyclerView.Adapter<MovieAdapter.ViewHolder>() {
    private lateinit var binding: ItemMovieBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemBinding =
            ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(movieList[position])
    }

    override fun getItemCount(): Int {
        return movieList.size
    }

    inner class ViewHolder(private val itemBinding: ItemMovieBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {

        init {
            itemBinding.itemCard.setOnClickListener {

            }
            itemBinding.itemStar.setOnClickListener{
                itemBinding.itemStar.isEnabled = false
                Handler().postDelayed({
                    itemBinding.itemStar.isEnabled = true

                }, 100)
            }
        }

        fun bindItem(movieModel: Result) {
            //itemBinding.titleTxtView.text = articleDetailModel.title
            Utils.showFadeInAnimOnText(
                itemView.context,
                itemBinding.titleTxtview,
                movieModel.title
            )

            //itemBinding.siteTxtView.text = articleDetailModel.source?.sourceModelName
            Utils.showBounceAnimOnText(
                itemView.context,
                itemBinding.ratingTxtview,
                movieModel.vote_average.toString()
            )


            Handler().postDelayed({
                if (!movieModel.poster_path.isNullOrEmpty()) {

                    Utils.setImage(itemBinding.itemImgView, "${Constants.IMG_URL}${movieModel.poster_path}")
                } else {
                    Log.d(
                        "DEBUG",
                        "NewsAdapter articleDetailModel.urlToImage is ${movieModel.poster_path}"
                    )
                    val dummyUrl =
                        "https://ichef.bbci.co.uk/news/1024/branded_news/D268/production/_118046835_screenshot2021-04-13at22.37.06.png"
                    Utils.setImage(itemBinding.itemImgView, dummyUrl)
                }
            }, 100)

        }

    }


}