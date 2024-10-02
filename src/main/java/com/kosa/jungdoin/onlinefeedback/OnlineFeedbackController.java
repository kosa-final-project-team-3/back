package com.kosa.jungdoin.onlinefeedback;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/feedback")
public class OnlineFeedbackController {
	private final OnlineFeedbackService onlineFeedbackService;

	@PostMapping("/generate")
	public ResponseEntity<OnlineFeedbackResponse> generateFeedback(@RequestBody OnlineFeedbackRequest feedbackRequest) {
		String generatedFeedback = onlineFeedbackService.generateFeedback(feedbackRequest.getQuestion(), feedbackRequest.getPreset());
		return ResponseEntity.ok(new OnlineFeedbackResponse(generatedFeedback));
	}

	@GetMapping("/generate/get")
	public ResponseEntity<OnlineFeedbackResponse> generateFeedbackGet(
			@RequestParam(required = true) String question,
			@RequestParam(required = true) String preset) {
		String generatedFeedback = onlineFeedbackService.generateFeedback(question, preset);
		return ResponseEntity.ok(new OnlineFeedbackResponse(generatedFeedback));
	}
}