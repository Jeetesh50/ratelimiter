Rate Limiter Service

	•	Core Component: Implements the rate limiting logic.
	•	Token Bucket Algorithm:
	•	Each user/client is assigned a bucket.
	•	Tokens are added to the bucket at a fixed rate.
	•	Requests consume tokens; if the bucket is empty, the request is rejected or delayed.
