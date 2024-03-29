package com.example.edesia.presentation;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;


public class Home extends AppCompatActivity {

    LoginActivity user = new LoginActivity();

    ImageButton randomizer, userMenu, groceryList, recipeSearch, googleVision, logout;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        randomizer = findViewById(R.id.Randomizer);
        userMenu = findViewById(R.id.UserMenu);
        groceryList = findViewById(R.id.GroceryList);
        recipeSearch = findViewById(R.id.RecipeSearch);
        googleVision = findViewById(R.id.OCR);
        logout = findViewById(R.id.LogOut);


        randomizer.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                openRandomizer();
            }
        });

        userMenu.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                openUserMenu();
            }
        });

        groceryList.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                openGroceryList();
            }
        });

        recipeSearch.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                openRecipeSearch();
            }
        });

        googleVision.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                openGoogleVision();
            }
        });

        logout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                LogOut();
            }
        });


    }

    public void openRandomizer(){
        Intent intent = new Intent(this, MealRandomizer.class);
        startActivity(intent);
    }

    public void openRecipeSearch(){
        Intent intent = new Intent(this, RecipeSearch.class);
        startActivity(intent);
    }


    public void openGroceryList(){
        Intent intent = new Intent(this, GroceryList.class);
        startActivity(intent);
    }


    public void openUserMenu(){
        Intent intent = new Intent(this, UserMenu.class);
        startActivity(intent);
    }


    public void openGoogleVision(){
        Intent intent = new Intent(this, OCR_Vision.class);
        startActivity(intent);
    }

    public void LogOut(){
        Intent intent = new Intent(this, LoginActivity.class);
        user.setCurrentUser("");
        startActivity(intent);
    }
}