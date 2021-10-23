package com.aldhykohar.mvvmnewsapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.aldhykohar.mvvmnewsapp.databinding.ItemArticlePreviewBinding
import com.aldhykohar.mvvmnewsapp.network.model.news.Articles
import com.bumptech.glide.Glide


/**
 * Created by aldhykohar on 10/22/2021.
 */
class NewsAdapter : RecyclerView.Adapter<NewsAdapter.ArticleViewHolder>() {
    private val differCallback = object : DiffUtil.ItemCallback<Articles>() {
        override fun areItemsTheSame(oldItem: Articles, newItem: Articles) =
            oldItem.url == newItem.url

        override fun areContentsTheSame(oldItem: Articles, newItem: Articles) = oldItem == newItem

    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val binding =
            ItemArticlePreviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ArticleViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
    }

    override fun getItemCount(): Int = differ.currentList.size

    inner class ArticleViewHolder(private val binding: ItemArticlePreviewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(article: Articles) {
            with(binding) {
                Glide.with(root.context)
                    .load(article.urlToImage)
                    .into(ivArticleImage)

                tvSource.text = article.source?.name
                tvTitle.text = article.title
                tvDescription.text = article.description
                tvPublishedAt.text = article.publishedAt
            }
            itemView.setOnClickListener {
                onItemClickListener?.invoke(article)
            }
        }
    }

    private var onItemClickListener: ((Articles) -> Unit)? = null

    fun setOnItemClickListener(listener: (Articles) -> Unit) {
        onItemClickListener = listener
    }
}
