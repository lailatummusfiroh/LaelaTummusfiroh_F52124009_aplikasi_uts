package com.laela_tummusfiroh_f52124009.aplikasi_uts;

import android.app.AlertDialog;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextWatcher;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private EditText etSearch;
    private ImageButton btnSearch;
    private ListView listViewFruits;
    private FruitAdapter adapter;
    private List<Fruit> fruitList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        initViews();
        initData();
        setupAdapter();
        setupListeners();
    }

    private void initViews() {
        etSearch = findViewById(R.id.etSearch);
        btnSearch = findViewById(R.id.btnSearch);
        listViewFruits = findViewById(R.id.listViewFruits);
    }

    private void initData() {
        fruitList = new ArrayList<>();
        fruitList.add(new Fruit(
                "Apel",
                "Malus domestica",
                "Vitamin C, Serat, Kalium",
                "Menjaga kesehatan jantung & pencernaan",
                R.drawable.apel
        ));
        fruitList.add(new Fruit(
                "Pisang",
                "Musa paradisiaca",
                "Vitamin B6, Kalium, Karbohidrat",
                "Sumber energi instan & mendukung fungsi otot",
                R.drawable.pisang
        ));
        fruitList.add(new Fruit(
                "Jeruk Sunkist",
                "Citrus sinensis",
                "Vitamin C, Asam Folat, Antioksidan",
                "Meningkatkan imunitas & kesehatan kulit",
                R.drawable.jeruk
        ));
        fruitList.add(new Fruit(
                "Mangga Harum Manis",
                "Mangifera indica",
                "Vitamin A, C, Serat Alami",
                "Menjaga kesehatan mata & sistem pencernaan",
                R.drawable.mangga
        ));
        fruitList.add(new Fruit(
                "Alpukat Mentega",
                "Persea americana",
                "Lemak Sehat, Vitamin E, Serat",
                "Mengontrol kolesterol & kesehatan otak",
                R.drawable.alpukat
        ));
        fruitList.add(new Fruit(
                "Stroberi Segar",
                "Fragaria x ananassa",
                "Vitamin C, Antioksidan, Mangan",
                "Mencegah penuaan dini & memelihara daya tahan",
                R.drawable.strawbery
        ));
        fruitList.add(new Fruit(
                "Anggur Ungu",
                "Vitis vinifera",
                "Resveratrol, Vitamin K, Kalium",
                "Menjaga kesehatan pembuluh darah & jantung",
                R.drawable.anggur
        ));
        fruitList.add(new Fruit(
                "Semangka Merah",
                "Citrullus lanatus",
                "Likopen, Air 92%, Vitamin A & C",
                "Mencegah dehidrasi & menjaga tekanan darah",
                R.drawable.semangka
        ));
        fruitList.add(new Fruit(
                "Nanas Madu",
                "Ananas comosus",
                "Enzim Bromelain, Vitamin C",
                "Melancarkan pencernaan & anti-inflamasi",
                R.drawable.nanas
        ));
        fruitList.add(new Fruit(
                "Durian Montong",
                "Durio zibethinus",
                "Asam Folat, Triptofan, Kalium",
                "Menjaga metabolisme & menambah energi",
                R.drawable.durian
        ));
    }

    private void setupAdapter() {
        adapter = new FruitAdapter(this, fruitList);
        listViewFruits.setAdapter(adapter);
    }

    private void setupListeners() {

        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                adapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });


        btnSearch.setOnClickListener(v -> {
            String query = etSearch.getText().toString().trim();
            adapter.getFilter().filter(query);
            Toast.makeText(MainActivity.this, "Mencari: " + (query.isEmpty() ? "Semua buah" : query), Toast.LENGTH_SHORT).show();
        });


        listViewFruits.setOnItemClickListener((parent, view, position, id) -> {
            Fruit selectedFruit = adapter.getItem(position);
            showFruitDetailDialog(selectedFruit);
        });
    }

    private void showFruitDetailDialog(Fruit fruit) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View dialogView = LayoutInflater.from(this).inflate(R.layout.item_fruit, null);

        ImageView img = dialogView.findViewById(R.id.imgFruit);
        TextView name = dialogView.findViewById(R.id.tvFruitName);
        TextView latin = dialogView.findViewById(R.id.tvLatinName);
        TextView nutrition = dialogView.findViewById(R.id.tvNutrition);
        TextView benefit = dialogView.findViewById(R.id.tvBenefit);

        img.setImageResource(fruit.getImageResId());
        name.setText(fruit.getName());
        latin.setText(createBoldLabel("Nama Latin: ", fruit.getLatinName()));
        nutrition.setText(createBoldLabel("Kandungan: ", fruit.getNutrition()));
        benefit.setText(createBoldLabel("Manfaat: ", fruit.getBenefit()));

        builder.setTitle("Detail Informasi Buah")
               .setView(dialogView)
               .setPositiveButton("Tutup", (dialog, which) -> dialog.dismiss())
               .create()
               .show();
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
}
