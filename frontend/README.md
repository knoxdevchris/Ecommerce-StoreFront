# StoreFront React Frontend

A modern, responsive ecommerce frontend built with React, Vite, and Bootstrap.

## 🚀 Tech Stack

- **React 18** - Component-based UI library
- **Vite** - Fast development build tool
- **React Router** - Client-side routing
- **Bootstrap 5** - Responsive UI framework
- **React Bootstrap** - Bootstrap components for React
- **Fetch API** - Native browser API for HTTP requests

## 📁 Project Structure

```
frontend/
├── src/
│   ├── components/     # Reusable UI components
│   │   └── Navbar.jsx
│   ├── contexts/       # React contexts
│   │   └── AuthContext.jsx
│   ├── pages/          # Page components
│   │   ├── Home.jsx
│   │   ├── Login.jsx
│   │   ├── Register.jsx
│   │   ├── Products.jsx
│   │   └── Cart.jsx
│   ├── App.jsx         # Main app component
│   ├── App.css         # Custom styles
│   └── main.jsx        # Entry point
├── package.json         # Dependencies and scripts
├── vite.config.js      # Vite configuration
└── index.html          # HTML template
```

## 🛠️ Getting Started

### Prerequisites
- Node.js (v16 or higher)
- npm or yarn
- Backend running on `http://localhost:8080`

### Installation

1. **Navigate to frontend directory:**
   ```bash
   cd frontend
   ```

2. **Install dependencies:**
   ```bash
   npm install
   ```

3. **Start development server:**
   ```bash
   npm run dev
   ```

4. **Open your browser:**
   - Frontend: `http://localhost:5173`
   - Backend: `http://localhost:8080`

## 🎯 Features

### ✅ Implemented
- **User Authentication**
  - Login/Register forms
  - JWT token management
  - Protected routes
  - User session persistence

- **Navigation**
  - Responsive navbar
  - Dynamic menu based on auth status
  - Client-side routing

- **Pages**
  - Homepage with hero section
  - Products listing
  - Shopping cart
  - User forms

- **UI/UX**
  - Bootstrap 5 components
  - Responsive design
  - Loading states
  - Error handling
  - Hover effects

### 🚧 Coming Soon
- Product search and filtering
- User profiles
- Order history
- Payment integration

## 🔧 Available Scripts

- `npm run dev` - Start development server
- `npm run build` - Build for production
- `npm run preview` - Preview production build
- `npm run lint` - Run ESLint

## 🌐 API Integration

The frontend connects to your Spring Boot backend at `http://localhost:8080/api`:

- **Authentication**: `/api/auth/*`
- **Products**: `/api/products`
- **Cart**: `/api/cart/*`
- **Orders**: `/api/orders/*`

## 🎨 Customization

### Styling
- Modify `src/App.css` for custom styles
- Override Bootstrap classes
- Add new CSS variables

### Components
- Extend existing components in `src/components/`
- Create new page components in `src/pages/`
- Add new contexts in `src/contexts/`

### Routing
- Add new routes in `src/App.jsx`
- Create new page components
- Update navigation as needed

## 📱 Responsive Design

- Mobile-first approach
- Bootstrap grid system
- Responsive navigation
- Touch-friendly interactions

## 🔒 Security Features

- JWT token authentication
- Protected routes
- Secure API calls
- Input validation
- XSS protection

## 🚀 Deployment

### Build for Production
```bash
npm run build
```

### Deploy Options
- **Netlify**: Drag and drop `dist/` folder
- **Vercel**: Connect GitHub repository
- **AWS S3**: Upload `dist/` contents
- **Traditional hosting**: Upload `dist/` files

## 🐛 Troubleshooting

### Common Issues

1. **Backend Connection Error**
   - Ensure backend is running on port 8080
   - Check CORS configuration
   - Verify API endpoints

2. **Build Errors**
   - Clear `node_modules` and reinstall
   - Check Node.js version compatibility
   - Verify all dependencies are installed

3. **Routing Issues**
   - Ensure React Router is properly configured
   - Check for conflicting routes
   - Verify navigation links

### Development Tips

- Use browser dev tools for debugging
- Check console for error messages
- Use React Developer Tools extension
- Monitor network requests in dev tools

## 📚 Learning Resources

- [React Documentation](https://react.dev/)
- [Vite Documentation](https://vitejs.dev/)
- [Bootstrap Documentation](https://getbootstrap.com/)
- [React Bootstrap Documentation](https://react-bootstrap.github.io/)

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Test thoroughly
5. Submit a pull request

## 📄 License

This project is part of the StoreFront ecommerce application.

---

**Happy coding! 🎉**
