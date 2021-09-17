package com.example.cst438_project01_group11.HomePageFragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.cst438_project01_group11.PokemonTypes;
import com.example.cst438_project01_group11.R;
import com.example.cst438_project01_group11.models.Pokemon;
import com.google.android.material.button.MaterialButton;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomPokemonFragment extends Fragment {

    private Pokemon mPokemon;
    private RandomFragmentInterface mInterface;

    private ImageView mPokemonImage;
    private TextView mPokemonName;
    private TextView mType1;
    private TextView mType2;
    private MaterialButton mAddToTeam;

    public interface RandomFragmentInterface {
        ArrayList<Pokemon> getPokemons();
    }

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.pokemon_layout, container, false);
        findViews(view);
        List<Pokemon> pokemons = mInterface.getPokemons();
        mPokemon = pokemons.get(new Random().nextInt(pokemons.size()));
        setPokemonInfo();
        return view;
    }

    private void setPokemonInfo() {
        setPokemonName();
        setTypeColors();
        setImage();
    }

    private void setImage() {
        Glide.with(getContext())
                .load(mPokemon.getImageUrl())
                .centerCrop()
                .placeholder(R.drawable.pokeball_icon)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(mPokemonImage);
    }

    private void setPokemonName() {
        mPokemonName.setText(mPokemon.getName());
    }

    private void setTypeColors() {
        if(mPokemon.getType1() != null) {
            mType1.setText(mPokemon.getType1());
            mType1.setBackgroundColor(PokemonTypes.getColor(mPokemon.getType1()));
        } else {
            mType1.setVisibility(View.GONE);
        }
        if(mPokemon.getType2() != null) {
            mType2.setText(mPokemon.getType2());
            mType2.setBackgroundColor(PokemonTypes.getColor(mPokemon.getType2()));
        } else {
            mType2.setVisibility(View.GONE);
        }
    }

    private void findViews(View view) {
        mPokemonImage = view.findViewById(R.id.pokemon_image);
        mPokemonName = view.findViewById(R.id.pokemon_name);
        mType1 = view.findViewById(R.id.pokemon_type_1_text);
        mType2 = view.findViewById(R.id.pokemon_type_2_text);
        mAddToTeam = view.findViewById(R.id.add_to_team_button);
        addListeners();
    }

    private void addListeners() {
        mAddToTeam.setOnClickListener(view -> Toast.makeText(getContext(), "Added pokemon to team", Toast.LENGTH_SHORT).show());
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof RandomFragmentInterface) {
            mInterface = (RandomFragmentInterface) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement RandomFragmentInterface");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mInterface = null;
    }
}
