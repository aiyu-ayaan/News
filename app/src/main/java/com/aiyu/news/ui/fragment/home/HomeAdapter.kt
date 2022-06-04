package com.aiyu.news.ui.fragment.home


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.aiyu.core.models.Article
import com.aiyu.news.databinding.RowNewsBinding
import com.aiyu.news.ui.utils.loadImage
import com.bumptech.glide.Glide

class HomeAdapter : ListAdapter<Article, HomeAdapter.HomeAdapterViewHolder>(DiffUtilArticle()) {

    class HomeAdapterViewHolder(
        private val binding: RowNewsBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(article: Article) = binding.apply {
            textViewTextTitle.text = article.title
            textViewSource.text = article.source.name
            article.urlToImage.loadImage(
                itemView,
                imageViewTitleImage,
                progressBarNews,
                10
            )
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
        holder.bind(getItem(position))
    }
}

class DiffUtilArticle() : DiffUtil.ItemCallback<Article>() {
    override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean =
        oldItem == newItem

    override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean =
        oldItem == newItem

}