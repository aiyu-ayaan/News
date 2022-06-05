package com.aiyu.news.ui.fragment.home


import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.aiyu.core.models.Article
import com.aiyu.news.R
import com.aiyu.news.databinding.RowNewsBinding
import com.aiyu.news.ui.utils.convertDateToTime
import com.aiyu.news.ui.utils.loadImage
import java.text.SimpleDateFormat

class HomeAdapter(
    private val listener: (Article) -> Unit,
    private val favListener: (Article, Boolean) -> Unit,
    private val shareClick: (String, String) -> Unit
) : PagingDataAdapter<Article, HomeAdapter.HomeAdapterViewHolder>(DiffUtilArticle()) {


    private var favoritesList: List<Article> = arrayListOf()

    @SuppressLint("NotifyDataSetChanged")
    fun setFavoritesList(list: List<Article>) {
        this.favoritesList = list
        notifyDataSetChanged()
    }

    inner class HomeAdapterViewHolder(
        private val binding: RowNewsBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                if (absoluteAdapterPosition != RecyclerView.NO_POSITION)
                    getItem(absoluteAdapterPosition)?.let {
                        listener.invoke(it)
                    }
            }

            binding.imageButtonFav.setOnClickListener {
                if (absoluteAdapterPosition != RecyclerView.NO_POSITION)
                    getItem(absoluteAdapterPosition)?.let {
                        favListener.invoke(it, favoritesList.contains(it))
                    }
            }
            binding.imageButtonShare.setOnClickListener {
                if (absoluteAdapterPosition != RecyclerView.NO_POSITION)
                    getItem(absoluteAdapterPosition)?.let {
                        shareClick.invoke(it.title, it.url.toString())
                    }
            }
        }

        @SuppressLint("SimpleDateFormat")
        fun bind(article: Article) = binding.apply {
            binding.root.transitionName = article.title
            textViewTextTitle.text = article.title
            textViewSource.text = article.source?.name
            article.urlToImage.loadImage(
                itemView,
                imageViewTitleImage,
                progressBarNews,
                10
            )
            if (favoritesList.contains(article))
                imageButtonFav.setImageResource(R.drawable.ic_favorite_bottom_nav)
            else
                imageButtonFav.setImageResource(R.drawable.ic_favorite)
            try {
                val date =
                    SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").parse(article.publishedAt!!)
                        ?.convertDateToTime()

                textViewSource.text = binding.root.context.getString(
                    R.string.author_with_date, article.source?.name, date
                )
            } catch (e: Exception) {
                Toast.makeText(binding.root.context, "${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeAdapterViewHolder =
        HomeAdapterViewHolder(
            RowNewsBinding.inflate(
                LayoutInflater.from(
                    parent.context
                ),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: HomeAdapterViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }
}

class DiffUtilArticle() : DiffUtil.ItemCallback<Article>() {
    override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean =
        oldItem == newItem

    override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean =
        oldItem == newItem

}