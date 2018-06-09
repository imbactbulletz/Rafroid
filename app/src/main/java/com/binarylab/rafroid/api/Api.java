package com.binarylab.rafroid.api;

import com.binarylab.rafroid.dto.CalendarsDTO;
import com.binarylab.rafroid.dto.ClassesDTO;
import com.binarylab.rafroid.dto.CompleteNewsDTO;
import com.binarylab.rafroid.dto.ConsultationsDTO;
import com.binarylab.rafroid.dto.ExamsDTO;
import com.binarylab.rafroid.dto.VersionDTO;
import com.binarylab.rafroid.services.ServicesGenerator;

import retrofit2.Call;
import retrofit2.http.GET;

import retrofit2.http.Headers;

public interface Api {


    @Headers("apikey: "+ServicesGenerator.apikey)
    @GET("exams-version")
    Call<VersionDTO> examsVersion();

    @Headers("apikey: "+ServicesGenerator.apikey)
    @GET("classes-version")
    Call<VersionDTO> classesVersion();

    @Headers("apikey: "+ServicesGenerator.apikey)
    @GET("calendar-version")
    Call<VersionDTO> calenderVersion();

    @Headers("apikey: "+ServicesGenerator.apikey)
    @GET("consultations-version")
    Call<VersionDTO> consultationVersion();

    @Headers("apikey: "+ServicesGenerator.apikey)
    @GET("news-version")
    Call<VersionDTO> newsVersion();

    @Headers("apikey: "+ServicesGenerator.apikey)
    @GET("exams")
    Call<ExamsDTO> getExams();

    @Headers("apikey: "+ServicesGenerator.apikey)
    @GET("classes")
    Call<ClassesDTO> getClasses();

    @Headers("apikey: "+ServicesGenerator.apikey)
    @GET("calendar")
    Call<CalendarsDTO> getCalendars();

    @Headers("apikey: "+ServicesGenerator.apikey)
    @GET("consultations")
    Call<ConsultationsDTO> getConsultations();

    @Headers("apikey: "+ServicesGenerator.apikey)
    @GET("news")
    Call<CompleteNewsDTO> getNews();


}
