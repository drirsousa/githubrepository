package br.com.rosait.githubrepository.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.com.rosait.githubrepository.R
import br.com.rosait.githubrespositories.data.model.RepositoryItem
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy

class RepositoryAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val ITEM = 0
    private val LOADING = 1
    var isLoadingAdded = false

    private var items: ArrayList<RepositoryItem> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view: View

        when (viewType) {
            ITEM -> {
                view = LayoutInflater.from(parent.context).inflate(R.layout.repository_item, parent, false)
                return ItemVH(view)
            }
            else -> {
                view = LayoutInflater.from(parent.context).inflate(R.layout.item_progress, parent, false)
                return LoadingVH(view)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if(position == items.size - 1 && isLoadingAdded) LOADING else ITEM
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            ITEM -> (holder as ItemVH).onBindViewHolder(items[position])
            else -> {
                //Nothing to do
            }
        }
    }

    override fun getItemCount(): Int = items.size

    fun add(repositoryItem: RepositoryItem) {
        items.add(repositoryItem)
        notifyItemInserted(items.size - 1)
    }

    fun addAll(repositoryItems: List<RepositoryItem>) {
        repositoryItems.forEach {
            add(it)
        }
    }

    fun getRepositoryItems() : ArrayList<RepositoryItem> {
        return items
    }

    fun addLoadingFooter() {
        isLoadingAdded = true
        add(RepositoryItem())
    }

    fun removeLoadingFooter() {
        isLoadingAdded = false
        val position = items.size - 1
        if(position >= 0) {
            val item = getItem(position)
            if(item != null) {
                items.removeAt(position)
                notifyItemRemoved(position)
            }
        }
    }

    fun getItem(position: Int) : RepositoryItem? {
        return items.get(position)
    }

    class ItemVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val repositoryName: TextView by lazy { itemView.findViewById(R.id.repositoryNameValue)  }
        private val starsQty: TextView by lazy { itemView.findViewById(R.id.starsQtyValue)  }
        private val forkQty: TextView by lazy { itemView.findViewById(R.id.forkQtyValue)  }
        private val ownerLoginName: TextView by lazy { itemView.findViewById(R.id.ownerLogin)  }
        private val ownerImageView: ImageView by lazy { itemView.findViewById(R.id.ownerImage)  }

        fun onBindViewHolder(repositoryItem: RepositoryItem) {
            with(itemView) {
                repositoryName.text = repositoryItem.name
                starsQty.text = "${repositoryItem.stargazers_count}"
                forkQty.text = "${repositoryItem.forks_count}"
                ownerLoginName.text = repositoryItem.ownerLogin.login

                Glide
                    .with(context)
                    .load(repositoryItem.ownerLogin.imageUrl)
                    .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                    .into(ownerImageView)
            }
        }
    }

    class LoadingVH(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        override fun onClick(view: View?) {
            //Nothing to do
        }
    }
}