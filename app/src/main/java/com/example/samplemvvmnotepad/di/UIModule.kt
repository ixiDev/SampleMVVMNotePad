package com.example.samplemvvmnotepad.di

import android.content.Context
import com.azeesoft.lib.colorpicker.ColorPickerDialog
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.android.qualifiers.ActivityContext

@Module
@InstallIn(FragmentComponent::class)
object UIModule {


    @Provides
    fun provideColorDialog(@ActivityContext context: Context): ColorPickerDialog {
        return ColorPickerDialog.createColorPickerDialog(context)
    }

}