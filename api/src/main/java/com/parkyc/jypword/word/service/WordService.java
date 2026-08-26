package com.parkyc.jypword.word.service;

import com.parkyc.jypword.word.repository.SentenceRepository;
import com.parkyc.jypword.word.repository.WordMeanRepository;
import com.parkyc.jypword.word.repository.WordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class WordService {

    private final WordRepository wordRepository;
    private final WordMeanRepository meanRepository;
    private final SentenceRepository sentenceRepository;


}
