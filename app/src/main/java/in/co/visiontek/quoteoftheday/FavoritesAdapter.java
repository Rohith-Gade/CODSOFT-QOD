package in.co.visiontek.quoteoftheday;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class FavoritesAdapter extends RecyclerView.Adapter<FavoritesAdapter.FavoriteViewHolder> {
    private Context context;
    private List<String> favoriteItems;

    public FavoritesAdapter(Context context, List<String> favoriteItems) {
        this.context = context;
        this.favoriteItems = favoriteItems;
    }

    @NonNull
    @Override
    public FavoriteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.favorite_item_layout, parent, false);
        return new FavoriteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteViewHolder holder, int position) {
        String favoriteItem = favoriteItems.get(position);
        holder.favoriteItemText.setText(favoriteItem);
    }

    @Override
    public int getItemCount() {
        return favoriteItems.size();
    }

    public class FavoriteViewHolder extends RecyclerView.ViewHolder {
        TextView favoriteItemText;

        public FavoriteViewHolder(@NonNull View itemView) {
            super(itemView);
            favoriteItemText = itemView.findViewById(R.id.favoriteText);
        }
    }
}
