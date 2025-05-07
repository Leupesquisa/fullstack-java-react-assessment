# E-Commerce Frontend Application

A complete, functional, and professional frontend for an e-commerce application built with React, TypeScript, Tailwind CSS, React Router, and Axios.

## Features

- **Authentication**: JWT-based authentication with login and registration
- **Product Management**: View, create, edit, and delete products
- **Responsive Design**: 100% responsive layout for mobile, tablet, and desktop
- **Modern UI**: Clean and professional design with Tailwind CSS
- **API Integration**: Seamless integration with the backend API

## Tech Stack

- **React 18**: Modern UI library for building user interfaces
- **TypeScript**: Type-safe JavaScript for better developer experience
- **Tailwind CSS**: Utility-first CSS framework for rapid UI development
- **React Router**: Navigation and routing for React applications
- **Axios**: Promise-based HTTP client for API requests
- **Vite**: Next-generation frontend tooling for faster development
- **Cypress**: End-to-end testing framework

## Project Structure

The project follows the Atomic Design methodology for component organization:

```
frontend/
├── public/                # Static assets
├── src/
│   ├── assets/            # Images, fonts, etc.
│   ├── components/        # UI components
│   │   ├── atoms/         # Basic building blocks (Button, Input)
│   │   ├── molecules/     # Combinations of atoms (ProductCard)
│   │   └── organisms/     # Complex UI sections (Navbar, Footer)
│   ├── context/           # React context providers
│   ├── pages/             # Application pages
│   ├── services/          # API services
│   ├── types/             # TypeScript type definitions
│   ├── App.tsx            # Main application component
│   ├── index.tsx          # Application entry point
│   └── styles.css         # Global styles and Tailwind imports
├── cypress/               # E2E tests
├── package.json           # Dependencies and scripts
└── tsconfig.json          # TypeScript configuration
```

## Getting Started

### Prerequisites

- Node.js 16+ and npm/yarn
- Backend API running at http://localhost:8080/api

### Installation

1. Clone the repository:
   ```bash
   git clone <repository-url>
   cd e-commerce-frontend
   ```

2. Install dependencies:
   ```bash
   npm install
   # or
   yarn install
   ```

### Running the Application

1. Start the development server:
   ```bash
   npm run dev
   # or
   yarn dev
   ```

2. Open your browser and navigate to http://localhost:3000

### Building for Production

```bash
npm run build
# or
yarn build
```

The build artifacts will be stored in the `dist/` directory.

### Running Tests

To run the Cypress E2E tests:

```bash
npm run test
# or
yarn test
```

For headless testing:

```bash
npm run test:headless
# or
yarn test:headless
```

## Connecting to the Backend

The application is configured to connect to a backend API running at http://localhost:8080/api. Make sure the backend server is running before using the application.

The API proxy is configured in `vite.config.ts` to forward all `/api` requests to the backend server.

## Authentication

The application uses JWT authentication. When a user logs in or registers, the JWT token is stored in localStorage and included in the Authorization header for all subsequent API requests.
