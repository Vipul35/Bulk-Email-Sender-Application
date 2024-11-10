🎯 Sending job applications can be exhausting! Many job seekers spend hours crafting and sending individual emails to recruiters, which takes up a lot of time and effort. To solve this problem, I built a tool that makes outreach much easier.

To solve this, I developed a Bulk Email Sender Application that makes outreach simpler, faster, and smarter by automating the process and ensuring follow-ups are handled automatically.

How it Works

Personalized Message Generation:
The tool allows users to simply upload their email list data in an Excel or Google Sheet format.
The user just needs to provide a prompt, and I’ve utilized Ollama and Mistral as large language models (LLMs) to generate email messages based on that prompt.

Data Processing and Storage:
Once the data is uploaded, it’s saved and then published to a Kafka topic for efficient processing.
Kafka’s asynchronous processing ensures that emails are sent without waiting for each task to complete sequentially, which speeds up the process significantly, especially when handling large volumes.

Concurrent Email Sending with Executor Service:
Given that candidates may reach out to a large number of recruiters at once, the tool uses Java’s Executor Service with a thread pool to handle concurrent email tasks.
This enables maximum CPU utilization, allowing multiple emails to be processed simultaneously, enhancing efficiency and saving time.

Automated Follow-ups Using Cron Jobs:
Recruiters often receive hundreds of emails, so follow-ups can make a significant difference in increasing response rates.
For this, I set up cron jobs that run daily at a specific time. These cron jobs check which emails haven’t received replies, based on data from the inbox and a cross-check with our database.
If a recruiter hasn’t responded by the follow-up date set by the user, the tool sends a follow-up email automatically, improving the chances of getting noticed.


Tech Stack: Java, Spring Boot, Kafka, SQL, Multithreading
