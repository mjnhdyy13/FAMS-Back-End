package com.example.hcm23_java14_team2.service.Impl;

import com.example.hcm23_java14_team2.exception.ApplicationException;
import com.example.hcm23_java14_team2.exception.NotFoundException;
import com.example.hcm23_java14_team2.exception.ValidationException;
import com.example.hcm23_java14_team2.model.entities.Enum.Level;
import com.example.hcm23_java14_team2.model.entities.Enum.StatusSyllabus;
import com.example.hcm23_java14_team2.model.entities.OutputStandard;
import com.example.hcm23_java14_team2.model.entities.Syllabus;
import com.example.hcm23_java14_team2.model.mapper.OutputStandardMapper;
import com.example.hcm23_java14_team2.model.mapper.SyllabusMapper;
import com.example.hcm23_java14_team2.model.request.Syllabus.SyllabusRequest;
import com.example.hcm23_java14_team2.model.response.*;

import com.example.hcm23_java14_team2.repository.OutputStandardRepository;
import com.example.hcm23_java14_team2.repository.SyllabusRepository;
import com.example.hcm23_java14_team2.service.SyllabusService;
import com.example.hcm23_java14_team2.util.ValidatorUtil;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class SyllabusServiceImpl implements SyllabusService {
    @Autowired
    private SyllabusRepository syllabusRepository;

    @Autowired
    private OutputStandardRepository outputStandardRepository;

    @Autowired
    private SyllabusMapper syllabusMapper;
    @Autowired
    private OutputStandardMapper outputStandardMapper;
    @Autowired
    private ValidatorUtil validatorUtil;

    @Override
    public Syllabus findByID(Long id) {
        try {
            return syllabusRepository.findById(id).orElse(null);
        } catch (ApplicationException ex) {
            throw ex;
        }
    }

    @Override
    public PageResponse<List<SyllabusResponse>> getAllSyllabusWithPage(String search, Integer page, Integer size) {
        var PageSyllabus = syllabusRepository.searchByNameWithPage(search,PageRequest.of(page-1,size));
        List<SyllabusResponse> syllabusResponseList = syllabusMapper.toResponselist(PageSyllabus.getContent());

        for(SyllabusResponse syllabusResponse : syllabusResponseList){
            List<OutputStandard> outputStandardList = outputStandardRepository.findOutputStandardBySyllabusId(syllabusResponse.getId());
            List<OutputStandardResponse> outputStandardResponses = outputStandardMapper.toResponseList(outputStandardList);
            syllabusResponse.setOutputStandardList(outputStandardResponses);
        }

        PageResponse<List<SyllabusResponse>> listPageResponse = new PageResponse<>();
        listPageResponse.ok(syllabusResponseList);
        double total = Math.ceil((double)PageSyllabus.getTotalElements() / size);
        listPageResponse.setTotalPage(total);
        return  listPageResponse;
    }

    @Override
    public ApiResponse<List<SyllabusResponse>> getAllSyllabus(String search) {
        List<SyllabusResponse> syllabusResponseList = syllabusMapper.toResponselist(syllabusRepository.searchByName(search));
        for(SyllabusResponse syllabusResponse : syllabusResponseList){
            List<OutputStandard> outputStandardList = outputStandardRepository.findOutputStandardBySyllabusId(syllabusResponse.getId());
            List<OutputStandardResponse> outputStandardResponses = outputStandardMapper.toResponseList(outputStandardList);
            syllabusResponse.setOutputStandardList(outputStandardResponses);
        }
        ApiResponse<List<SyllabusResponse>> apiResponse = new ApiResponse<>();
        apiResponse.ok(syllabusResponseList);
        return apiResponse;
    }


    @Override
    public SyllabusResponse findById(Long id) {
        SyllabusResponse syllabusResponse = syllabusMapper.toResponse(syllabusRepository.findById(id).orElse(null));
        if (syllabusResponse == null) {
            throw new NotFoundException("");
        }
        return syllabusResponse;
    }

    @Transactional
    @Override
    public SyllabusResponse updateSyllabus(Long id, SyllabusRequest syllabusRequest, BindingResult bindingResult) {
        try {
            Syllabus existingSyllabus = syllabusRepository.findById(id)
                    .orElseThrow(() -> new NotFoundException("Syllabus Not Found"));

            if (bindingResult.hasErrors()) {
                Map<String, String> validationErrors = validatorUtil.toErrors(bindingResult.getFieldErrors());
                throw new ValidationException(validationErrors);
            }
            if (syllabusRequest.getCodeName() != null) {
                existingSyllabus.setCodeName(syllabusRequest.getCodeName());
            }
            if (syllabusRequest.getTopicName() != null) {
                existingSyllabus.setTopicName(syllabusRequest.getTopicName());
            }
            if (syllabusRequest.getTechnicalReq() != null) {
                existingSyllabus.setTechnicalReq(syllabusRequest.getTechnicalReq());
            }
            if (syllabusRequest.getVersion() != 0.0f) {
                existingSyllabus.setVersion(syllabusRequest.getVersion());
            }
            // if (syllabusRequest.getDuration() != null) {
            // existingSyllabus.setDuration(syllabusRequest.getDuration());
            // }
            if (syllabusRequest.getCourseObjective() != null) {
                existingSyllabus.setCourseObjective(syllabusRequest.getCourseObjective());
            }
            if (syllabusRequest.getLevel() != null) {
                existingSyllabus.setLevel(syllabusRequest.getLevel());
            }
            if (syllabusRequest.getPrinciple() != null) {
                existingSyllabus.setPrinciple(syllabusRequest.getPrinciple());
            }
            if (syllabusRequest.getStatus() != null) {
                existingSyllabus.setStatus(syllabusRequest.getStatus());
            }
            syllabusRepository.saveAndFlush(existingSyllabus);
            return syllabusMapper.toResponse(existingSyllabus);
        } catch (ApplicationException ex) {
            throw ex;
        }
    }

    @Override
    public ApiResponse<Object> insertSyllabus(SyllabusRequest request) {

        var syllabus = Syllabus.builder()
                .codeName(request.getCodeName())
                .topicName(request.getTopicName())
                .technicalReq(request.getTechnicalReq())
                .version(request.getVersion())
                .duration(0)
                .courseObjective(request.getCourseObjective())
                .level(Level.ALL_LEVEL)
                .principle(request.getPrinciple())
                .status(request.getStatus())
                .build();
        try {
            syllabusRepository.save(syllabus);
            var response = ApiResponse
                    .builder()
                    .statusCode("200")
                    .message("Insert successes")
                    .data(syllabusMapper.toResponse(syllabus))
                    .build();
            return response;
        } catch (Exception e) {
            return ApiResponse
                    .builder()
                    .statusCode("401")
                    .message("Insert failed")
                    .build();
        }
    }

    @Transactional
    @Override
    public String deleteSyllabus(Long id) {
        Syllabus syllabus = syllabusRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy giáo trình"));
        syllabus.setStatus(StatusSyllabus.INACTIVE);
        syllabusRepository.save(syllabus);
        return "Xóa thành công";
    }

}
