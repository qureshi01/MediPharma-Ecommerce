import React from 'react'

const CategoryProductTile = ({product}) => {
  return (
    <div className='products-par' key = {product.id}>
    <div className='products-tile' key={product.id}>
        <img className='prod-image' src={product.image} alt={product.title} />
        <div className='prod-star'>
            <h4 className='prod-name'>{product.title}</h4>
            {firstName && 
                    renderButtons(product)
            }
        </div>
        <p className='prod-disc-price'>Rs.{(product.price - product.disc_price).toFixed(2)} <del className='prod-price'>{product.price}</del></p>
        <p className='save'>You Save Rs.{product.disc_price}</p>
        <span className='del'>Delivery in 5 days</span>
    </div>
</div>
  )
}

export default CategoryProductTile
