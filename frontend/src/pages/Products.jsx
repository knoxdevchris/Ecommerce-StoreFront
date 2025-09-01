import React, { useState, useEffect } from 'react'
import { Container, Row, Col, Card, Button, Alert, Spinner } from 'react-bootstrap'
import { useAuth } from '../contexts/AuthContext'

const Products = () => {
  const [products, setProducts] = useState([])
  const [loading, setLoading] = useState(true)
  const [error, setError] = useState('')
  const { isAuthenticated, authToken, API_BASE_URL } = useAuth()

  useEffect(() => {
    loadProducts()
  }, [])

  const loadProducts = async () => {
    try {
      const response = await fetch(`${API_BASE_URL}/products`)
      if (response.ok) {
        const data = await response.json()
        setProducts(data)
      } else {
        setError('Failed to load products')
      }
    } catch (error) {
      console.error('Error loading products:', error)
      setError('Error loading products. Please try again.')
    } finally {
      setLoading(false)
    }
  }

  const addToCart = async (productId) => {
    if (!isAuthenticated) {
      alert('Please login to add items to cart')
      return
    }

    try {
      const response = await fetch(`${API_BASE_URL}/cart`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
          'Authorization': `Bearer ${authToken}`
        },
        body: JSON.stringify({
          userId: 1, // This should come from user context
          productId: productId,
          quantity: 1
        })
      })

      if (response.ok) {
        alert('Added to cart!')
      } else {
        alert('Failed to add to cart')
      }
    } catch (error) {
      console.error('Error adding to cart:', error)
      alert('Failed to add to cart')
    }
  }

  if (loading) {
    return (
      <Container className="text-center">
        <Spinner animation="border" role="status">
          <span className="visually-hidden">Loading...</span>
        </Spinner>
      </Container>
    )
  }

  if (error) {
    return (
      <Container>
        <Alert variant="danger">{error}</Alert>
      </Container>
    )
  }

  return (
    <Container>
      <h1 className="mb-4">Our Products</h1>
      
      {products.length === 0 ? (
        <Alert variant="info">
          No products available at the moment. Please check back later!
        </Alert>
      ) : (
        <Row>
          {products.map((product) => (
            <Col key={product.id} lg={4} md={6} className="mb-4">
              <Card className="h-100">
                <div className="bg-light text-center p-4" style={{ height: '200px' }}>
                  {product.imageUrl ? (
                    <img 
                      src={product.imageUrl} 
                      alt={product.name}
                      style={{ maxWidth: '100%', maxHeight: '100%', objectFit: 'contain' }}
                    />
                  ) : (
                    <div className="d-flex align-items-center justify-content-center h-100 text-muted">
                      No Image
                    </div>
                  )}
                </div>
                <Card.Body className="d-flex flex-column">
                  <Card.Title>{product.name}</Card.Title>
                  <Card.Text>{product.description}</Card.Text>
                  <div className="mt-auto">
                    <div className="d-flex justify-content-between align-items-center mb-3">
                      <span className="h5 text-primary mb-0">${product.price}</span>
                      <small className="text-muted">Stock: {product.stockQuantity}</small>
                    </div>
                    <Button 
                      variant="primary" 
                      onClick={() => addToCart(product.id)}
                      className="w-100"
                    >
                      Add to Cart
                    </Button>
                  </div>
                </Card.Body>
              </Card>
            </Col>
          ))}
        </Row>
      )}
    </Container>
  )
}

export default Products
