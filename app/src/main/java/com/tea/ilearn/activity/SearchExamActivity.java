package com.tea.ilearn.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.chip.Chip;
import com.tea.ilearn.activity.exercise_list.ExerciseListActivity;
import com.tea.ilearn.databinding.ActivitySearchExamBinding;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SearchExamActivity extends AppCompatActivity {
    private ActivitySearchExamBinding binding;
    private Map<String, Integer> map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySearchExamBinding.inflate(getLayoutInflater());
        View root = binding.getRoot();

        map = new HashMap<>();

        binding.plus.setOnClickListener($ -> {
            String name = binding.entityName.getText().toString();
            if (name.isEmpty()) {
                Toast.makeText(root.getContext(), "知识点不能为空", Toast.LENGTH_SHORT).show();
            } else {
                if (map.get(name) != null) {
                    Toast.makeText(root.getContext(), "知识点不能重复", Toast.LENGTH_SHORT).show();
                    return;
                }
                map.put(name, 1);
                Chip chip = new Chip(root.getContext());
                chip.setText(name);
                chip.setCloseIconVisible(true);
                chip.setOnCloseIconClickListener($$ -> {
                    binding.chipgroup.removeView(chip);
                    map.remove(name);
                });
                binding.entityName.setText("");
                binding.chipgroup.addView(chip);
            }
        });

        binding.submit.setOnClickListener($ -> {
            if (map.isEmpty()) {
                Toast.makeText(root.getContext(), "请选择至少一个知识点", Toast.LENGTH_SHORT).show();
                return;
            }
            Intent intent = new Intent(root.getContext(), ExerciseListActivity.class);
            intent.setAction(Intent.ACTION_SEARCH);
            intent.putStringArrayListExtra("entities", new ArrayList<>(map.keySet()));
            startActivity(intent);
        });

        setContentView(root);
    }
}