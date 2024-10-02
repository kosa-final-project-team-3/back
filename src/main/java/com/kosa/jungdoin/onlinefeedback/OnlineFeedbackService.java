package com.kosa.jungdoin.onlinefeedback;

import java.io.File;

import ai.djl.huggingface.tokenizers.Encoding;
import ai.djl.huggingface.tokenizers.HuggingFaceTokenizer;
import ai.djl.Model;
import ai.djl.ndarray.NDList;
import ai.djl.ndarray.NDManager;
import ai.djl.inference.Predictor;
import ai.djl.translate.TranslateException;
import ai.djl.translate.Translator;
import ai.djl.translate.TranslatorContext;
import org.springframework.stereotype.Service;

@Service
public class OnlineFeedbackService {

	public String generateFeedback(String question, String preset) {
		String input = preset + " " + question;

		try (NDManager manager = NDManager.newBaseManager()) {

			// 모델과 토크나이저 생성 시 에러 추적을 위한 try-catch 추가
			Model model;
			HuggingFaceTokenizer tokenizer;

			// 모델 및 토크나이저 초기화
			try {
				// 모델 초기화
				File modelPath = new File("C:/Users/KOSA/.djl.ai/model/gpt2");
				model = Model.newInstance(modelPath.toString());
				System.out.println("Model Loaded: " + model.getBlock());
				if (model.getBlock() == null) {
					throw new IllegalStateException("Model block is not initialized");
				}

				// 토크나이저 초기화
				tokenizer = HuggingFaceTokenizer.newInstance(modelPath.toString());

				// 모델 및 토크나이저가 성공적으로 로드되었음을 알리는 로그
				System.out.println("Model and Tokenizer loaded successfully.");
			} catch (Exception e) {
				e.printStackTrace(); // 구체적인 에러 로그 출력
				return "Error initializing model or tokenizer: " + e.getMessage();
			}

			// 입력 텍스트를 토큰화하고 모델로 예측 수행
			Encoding encoding = tokenizer.encode(input);
			NDList inputIds = new NDList(manager.create(encoding.getIds()));

			Translator<NDList, String> translator = new Translator<NDList, String>() {
				@Override
				public NDList processInput(TranslatorContext ctx, NDList input) {
					return input;
				}

				@Override
				public String processOutput(TranslatorContext ctx, NDList list) {
					return list.singletonOrThrow().toString();
				}
			};

			try (Predictor<NDList, String> predictor = model.newPredictor(translator)) {
				String output = predictor.predict(inputIds);
				return output;
			} catch (TranslateException e) {
				e.printStackTrace(); // 예측 실패 시 에러 출력
				return "Error generating feedback: " + e.getMessage();
			}

		} catch (Exception e) {
			e.printStackTrace(); // 기타 에러 출력
			return "Error loading model or generating feedback: " + e.getMessage();
		}
	}
}
