# SI Company ERP System

## 1. Project Overview
### 1.1 Project Description
- A comprehensive Enterprise Resource Planning (ERP) system designed for SI Company
- Built with Vue.js 3 (Frontend) and Spring Boot 3 (Backend)
- Focused on providing integrated management of company resources and operations

### 1.2 Technology Stack
#### Frontend
- Vue.js 3
- Pinia (State Management)
- Vue Router
- Tailwind CSS
- Lucide Icons
- Axios for API communication
- Vuelidate for form validation

#### Backend
- Spring Boot 3
- Spring Security with JWT
- Spring Data JPA
- MySQL Database
- Redis (for token management)
- Swagger UI for API documentation

### 1.3 Development Environment
- Java 21
- Node.js 20.10.0
- npm 10.2.3
- Gradle 8.x

## 2. System Architecture

### 2.1 Frontend Architecture
```
frontend/
├── src/
│   ├── components/      # Reusable UI components
│   ├── layouts/         # Page layouts
│   ├── views/           # Page components
│   ├── stores/          # Pinia stores
│   ├── router/          # Vue Router configuration
│   ├── config/          # Configuration files
│   └── plugins/         # Vue plugins
```

### 2.2 Backend Architecture
```
backend/
├── controller/          # REST API endpoints
├── service/            # Business logic
├── repository/         # Data access layer
├── entity/            # Database entities
├── dto/               # Data transfer objects
├── config/            # Configuration classes
└── security/          # Security configuration
```

## 3. Key Features

### 3.1 Authentication & Authorization
- JWT-based authentication
- Role-based access control (ADMIN, USER)
- Secure password handling with BCrypt
- Token refresh mechanism
- Login history tracking

### 3.2 User Management
- User registration and profile management
- Password reset functionality
- User status management (active/inactive)
- Login history and audit trails

### 3.3 Dashboard
- Overview of key metrics
- Real-time updates
- Customizable widgets
- Performance indicators

### 3.4 Human Resources
- Employee management
- Department organization
- Attendance tracking
- Leave management
- Performance evaluations

### 3.5 Project Management
- Project creation and tracking
- Task assignment
- Progress monitoring
- Resource allocation
- Timeline management

### 3.6 Reports & Analytics
- Customizable reports
- Data visualization
- Export functionality
- Performance analytics

## 4. Security Features

### 4.1 Frontend Security
- Protected routes
- Token-based authentication
- Secure localStorage handling
- CORS configuration
- XSS protection

### 4.2 Backend Security
- Spring Security implementation
- JWT token validation
- Password encryption
- Role-based authorization
- API request validation

## 5. Database Schema

### 5.1 Core Tables
- members: User information and authentication
- login_history: Authentication audit trails
- roles: User role definitions
- departments: Organization structure
- projects: Project management data
- tasks: Project task details

## 6. API Integration

### 6.1 REST API Structure
- /api/auth/: Authentication endpoints
- /api/members/: User management
- /api/projects/: Project operations
- /api/hr/: Human resources management
- /api/reports/: Reporting and analytics

### 6.2 Swagger Documentation
- Interactive API documentation
- Request/Response examples
- Authentication documentation
- Error handling documentation

## 7. Development & Deployment

### 7.1 Development Setup
- Local development environment
- Database configuration
- Redis setup
- Frontend development server
- Backend development server

### 7.2 Production Deployment
- Production build process
- Environment configuration
- Server requirements
- Deployment checklist

## 8. Future Enhancements

### 8.1 Planned Features
- Advanced analytics dashboard
- Mobile application support
- Real-time notifications
- Document management system
- Integration with external services

### 8.2 Scalability Considerations
- Microservices architecture
- Load balancing
- Caching strategies
- Database optimization