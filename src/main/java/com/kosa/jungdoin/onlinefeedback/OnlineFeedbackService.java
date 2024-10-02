package com.kosa.jungdoin.onlinefeedback;

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
			HuggingFaceTokenizer tokenizer = HuggingFaceTokenizer.newInstance("gpt2");
			Encoding encoding = tokenizer.encode(input);
			NDList inputIds = new NDList(manager.create(encoding.getIds()));

			Model model = Model.newInstance("gpt2");

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
			}
		} catch (TranslateException e) {
			return "Error generating feedback";
		} catch (Exception e) {
			return "Error loading model";
		}
	}
}