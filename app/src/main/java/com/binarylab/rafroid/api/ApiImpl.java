package com.binarylab.rafroid.api;

import com.binarylab.rafroid.dto.CalendarsDTO;
import com.binarylab.rafroid.dto.ClassesDTO;
import com.binarylab.rafroid.dto.ExamsDTO;
import com.binarylab.rafroid.dto.VersionDTO;
import com.binarylab.rafroid.services.ServicesGenerator;

import java.io.IOException;

import retrofit2.Call;

public class ApiImpl {

    private static ApiImpl INSTANCE = null;
    private Api api;

    private ApiImpl() {
        api = ServicesGenerator.createService(Api.class);
    }

    public static ApiImpl getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ApiImpl();
        }
        return(INSTANCE);
    }


    public VersionDTO examsVersion() throws IOException {
        Call<VersionDTO> call = api.examsVersion();
        return call.execute().body();
    }

    public VersionDTO classesVersion() throws IOException {
        Call<VersionDTO> call = api.classesVersion();
        return call.execute().body();
    }

    public VersionDTO calenderVersion() throws IOException {
        Call<VersionDTO> call = api.calenderVersion();
        return call.execute().body();
    }

    public VersionDTO consultationVersion() throws IOException {
        Call<VersionDTO> call = api.consultationVersion();
        return call.execute().body();
    }

    public VersionDTO newsVersion() throws IOException {
        Call<VersionDTO> call = api.newsVersion();
        return call.execute().body();
    }

    public ExamsDTO getExams() throws IOException{
        Call<ExamsDTO> call = api.getExams();
        return call.execute().body();
    }

    public ClassesDTO getClasses() throws IOException{
        Call<ClassesDTO> call = api.getClasses();
        return call.execute().body();
    }

    public CalendarsDTO getCalendars() throws IOException{
        Call<CalendarsDTO> call = api.getCalendars();
        return call.execute().body();
    }
}
