import React, { useState, useEffect } from 'react'
import { Container, Row, Col, Card, Button, Alert, Spinner, ListGroup } from 'react-bootstrap'
import { useAuth } from '../contexts/AuthContext'

const Cart = () => {
  const [cartItems, setCartItems] = useState([])
  const [loading, setLoading] = useState(true)
  const [error, setError] = useState('')
  const { currentUser, authToken, API_BASE_URL } = useAuth()

  useEffect(() => {
    loadCart()
  }, [])

  const loadCart = async () => {
    try {
      const response = await fetch(`${API_BASE_URL}/cart/user/${currentUser.id}`, {
        headers: {
          'Authorization': `Bearer ${authToken}`
        }
      })
      
      if (response.ok) {
        const data = await response.json()
        setCartItems(data)
      } else {
        setError('Failed to load cart')
      }
    } catch (error) {
      console.error('Error loading cart:', error)
      setError('Error loading cart')
    } finally {
      setLoading(false)
    }
  }

  const removeFromCart = async (cartItemId) => {
    try {
      const response = await fetch(`${API_BASE_URL}/cart/${cartItemId}`, {
        method: 'DELETE',
        headers: {
          'Authorization': `Bearer ${authToken}`
        }
      })

      if (response.ok) {
        loadCart() // Reload cart
      } else {
        alert('Failed to remove item')
      }
    } catch (error) {
      console.error('Error removing item:', error)
      alert('Failed to remove item')
    }
  }

  const checkout = () => {
    alert('Checkout functionality coming soon!')
  }

  const calculateTotal = () => {
    // This is a simplified calculation - you'll need to fetch product details
    return cartItems.reduce((sum, item) => sum + (item.quantity || 0), 0)
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
      <h1 className="mb-4">Shopping Cart</h1>
      
      {cartItems.length === 0 ? (
        <Alert variant="info">
          Your cart is empty. <a href="/products">Start shopping</a> to add items!
        </Alert>
      ) : (
        <Row>
          <Col lg={8}>
            <Card>
              <Card.Header>
                <h4>Cart Items ({cartItems.length})</h4>
              </Card.Header>
              <Card.Body>
                <ListGroup variant="flush">
                  {cartItems.map((item) => (
                    <ListGroup.Item key={item.id} className="d-flex justify-content-between align-items-center">
                      <div>
                        <strong>Product ID: {item.productId}</strong>
                        <br />
                        <small className="text-muted">Quantity: {item.quantity}</small>
                      </div>
                      <Button 
                        variant="outline-danger" 
                        size="sm"
                        onClick={() => removeFromCart(item.id)}
                      >
                        Remove
                      </Button>
                    </ListGroup.Item>
                  ))}
                </ListGroup>
              </Card.Body>
            </Card>
          </Col>
          
          <Col lg={4}>
            <Card>
              <Card.Header>
                <h4>Order Summary</h4>
              </Card.Header>
              <Card.Body>
                <div className="d-flex justify-content-between mb-2">
                  <span>Items:</span>
                  <span>{cartItems.length}</span>
                </div>
                <div className="d-flex justify-content-between mb-3">
                  <strong>Total:</strong>
                  <strong className="text-primary">${calculateTotal().toFixed(2)}</strong>
                </div>
                <Button 
                  variant="primary" 
                  size="lg" 
                  className="w-100"
                  onClick={checkout}
                >
                  Proceed to Checkout
                </Button>
              </Card.Body>
            </Card>
          </Col>
        </Row>
      )}
    </Container>
  )
}

export default Cart
