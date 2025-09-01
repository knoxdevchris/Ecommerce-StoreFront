import React from 'react'
import { Container, Row, Col, Card, Button } from 'react-bootstrap'
import { useNavigate } from 'react-router-dom'

const Home = () => {
  const navigate = useNavigate()

  return (
    <Container>
      {/* Hero Section */}
      <Row className="text-center mb-5">
        <Col>
          <div className="bg-primary text-white p-5 rounded">
            <h1 className="display-4 mb-3">Welcome to StoreFront</h1>
            <p className="lead mb-4">Your one-stop shop for everything you need</p>
            <Button
              variant="light"
              size="lg"
              onClick={() => navigate('/products')}
            >
              Shop Now
            </Button>
          </div>
        </Col>
      </Row>

      {/* Features Section */}
      <Row className="mb-5 justify-content-center">
        <Col md={4} className="mb-4">
          <Card className="h-100 text-center">
            <Card.Body>
              <Card.Title>🚚 Fast Shipping</Card.Title>
              <Card.Text>
                Get your orders delivered quickly with our reliable shipping service.
              </Card.Text>
            </Card.Body>
          </Card>
        </Col>
        <Col md={4} className="mb-4">
          <Card className="h-100 text-center">
            <Card.Body>
              <Card.Title>⭐ Quality Products</Card.Title>
              <Card.Text>
                We only sell the best products from trusted manufacturers.
              </Card.Text>
            </Card.Body>
          </Card>
        </Col>
      </Row>

      {/* Call to Action */}
      <Row className="text-center">
        <Col>
          <Card className="bg-light">
            <Card.Body className="p-5">
              <h2>Ready to Start Shopping?</h2>
              <p className="lead mb-4">
                Browse our extensive collection of products and find exactly what you need.
              </p>
              <Button
                variant="primary"
                size="lg"
                onClick={() => navigate('/products')}
              >
                View Products
              </Button>
            </Card.Body>
          </Card>
        </Col>
      </Row>
    </Container>
  )
}

export default Home
