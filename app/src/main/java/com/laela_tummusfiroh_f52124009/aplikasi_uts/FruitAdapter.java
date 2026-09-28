package com.laela_tummusfiroh_f52124009.aplikasi_uts;

import android.content.Context;
import android.graphics.Typeface;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class FruitAdapter extends BaseAdapter implements Filterable {

    private final List<Fruit> originalList;
    private List<Fruit> filteredList;
    private final LayoutInflater inflater;
    private FruitFilter filter;

    public FruitAdapter(Context context, List<Fruit> fruitList) {
        this.originalList = new ArrayList<>(fruitList);
        this.filteredList = new ArrayList<>(fruitList);
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return filteredList.size();
    }

    @Override
    public Fruit getItem(int position) {
        return filteredList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private static class ViewHolder {
        ImageView imgFruit;
        TextView tvFruitName;
        TextView tvLatinName;
        TextView tvNutrition;
        TextView tvBenefit;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        View rowView = convertView;

        if (rowView == null) {
            rowView = inflater.inflate(R.layout.item_fruit, parent, false);
            holder = new ViewHolder();
            holder.imgFruit = rowView.findViewById(R.id.imgFruit);
            holder.tvFruitName = rowView.findViewById(R.id.tvFruitName);
            holder.tvLatinName = rowView.findViewById(R.id.tvLatinName);
            holder.tvNutrition = rowView.findViewById(R.id.tvNutrition);
            holder.tvBenefit = rowView.findViewById(R.id.tvBenefit);
            rowView.setTag(holder);
        } else {
            holder = (ViewHolder) rowView.getTag();
        }

        Fruit fruit = getItem(position);

        holder.imgFruit.setImageResource(fruit.getImageResId());
        holder.tvFruitName.setText(fruit.getName());
        holder.tvLatinName.setText(createBoldLabel("Nama Latin: ", fruit.getLatinName()));
        holder.tvNutrition.setText(createBoldLabel("Kandungan: ", fruit.getNutrition()));
        holder.tvBenefit.setText(createBoldLabel("Manfaat: ", fruit.getBenefit()));

        return rowView;
    }

    private SpannableString createBoldLabel(String label, String value) {
        String fullText = label + value;
        SpannableString spannableString = new SpannableString(fullText);
        spannableString.setSpan(
                new StyleSpan(Typeface.BOLD),
                0,
                label.length(),
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        );
        return spannableString;
    }

    @Override
    public Filter getFilter() {
        if (filter == null) {
            filter = new FruitFilter();
        }
        return filter;
    }

    private class FruitFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();

            if (constraint == null || constraint.toString().trim().isEmpty()) {
                results.values = new ArrayList<>(originalList);
                results.count = originalList.size();
            } else {
                String query = constraint.toString().toLowerCase().trim();
                List<Fruit> matchResults = new ArrayList<>();

                for (Fruit fruit : originalList) {
                    if (fruit.getName().toLowerCase().contains(query) ||
                        fruit.getLatinName().toLowerCase().contains(query) ||
                        fruit.getNutrition().toLowerCase().contains(query) ||
                        fruit.getBenefit().toLowerCase().contains(query)) {
                        matchResults.add(fruit);
                    }
                }

                results.values = matchResults;
                results.count = matchResults.size();
            }

            return results;
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            filteredList = (List<Fruit>) results.values;
            notifyDataSetChanged();
        }
    }
}
