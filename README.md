# IntelliContentAdvisor
 IntelliContentAdvisor is a Java application that manages user interactions with content. It allows for creating users, adding content, recording interactions (such as views and likes), and retrieving user interaction histories. The system uses JDBC for database operations and handles user and content data to provide insights on user behavior.

 Usage
Creating Users: Users can be created using the user management interface.
Adding Content: New content can be added to the system via the content management interface.
Recording Interactions: The application records user interactions with content, such as views and likes.
Retrieving User Histories: Retrieve a user's interaction history to analyze behavior and generate recommendations.

Features
User Management: Create, update, and delete users.
Content Management: Add and manage content items.
Interaction Tracking: Record and analyze user interactions with content.
Recommendation Engine: Generate insights and recommendations based on user behavior.
Database Schema
The database schema includes the following tables:

Users: Stores user details such as user_id, name, and email.
Content: Stores content details such as content_id, title, and description.
Interactions: Logs interactions such as interaction_id, user_id, content_id, and interaction_type (e.g., view, like).
