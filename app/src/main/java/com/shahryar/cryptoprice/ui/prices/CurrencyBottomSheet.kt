package com.shahryar.cryptoprice.ui.prices

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.shahryar.cryptoprice.R
import com.shahryar.shared.data.model.CurrencyDto

@Composable
fun CurrencyBottomSheetView(currency: CurrencyDto) {
    Column(
        modifier = Modifier.padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = currency.name,
                fontWeight = FontWeight.Medium,
                fontSize = 22.sp,
                color = colorResource(
                    id = R.color.text_color
                )
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = "$${String.format("%.2f", currency.price)}",
                fontWeight = FontWeight.Medium,
                fontSize = 23.sp,
                color = colorResource(
                    id = R.color.text_color
                )
            )
        }
        Spacer(modifier = Modifier.height(30.dp))
        PercentCard(currency = currency)
        Spacer(modifier = Modifier.height(30.dp))
        OverView(currency)
        Spacer(modifier = Modifier.height(30.dp))
        Text(
            text = currency.last_updated,
            fontWeight = FontWeight.Light,
            fontSize = 13.sp,
            color = colorResource(
                id = R.color.text_alpha
            )
        )
    }
}

@Composable
fun OverView(currency: CurrencyDto) {
    Column(
        modifier = Modifier
            .padding(start = 15.dp, end = 15.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row {
            Column(modifier = Modifier
                .weight(1f)) {
                ItemOverview(label = "Total supply", value = "$${String.format("%.2f", currency.total_supply)}")
            }
            Spacer(modifier = Modifier.weight(0.2f))
            Column(modifier = Modifier.weight(1f)) {
                ItemOverview(
                    label = "Circulating supply",
                    value = "$${String.format("%.2f",currency.circulating_supply)}"
                )
            }
        }
        Spacer(modifier = Modifier.height(20.dp))
        Row {
            Column(modifier = Modifier.weight(1f)) {
                ItemOverview(label = "Max supply", value = "$${String.format("%.2f",currency.max_supply)}")
            }
            Spacer(modifier = Modifier.weight(0.2f))
            Column(modifier = Modifier.weight(1f)) {
                ItemOverview(label = "24 Hour volume", value = "$${String.format("%.2f", currency.volume_24h)}")
            }
        }
        Spacer(modifier = Modifier.height(20.dp))
        Row {
            Column(modifier = Modifier.weight(1f)) {
                ItemOverview(label = "Market cap", value = "$${currency.market_cap}")
            }
            Spacer(modifier = Modifier.weight(0.2f))
            Column(modifier = Modifier.weight(1f)) {
                ItemOverview(label = "CMC Rank", value = "${currency.cmc_rank}")
            }
        }
    }
}

@Composable
fun ItemOverview(label: String, value: String) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            modifier = Modifier.padding(bottom = 5.dp),
            text = label,
            fontWeight = FontWeight.Normal,
            fontSize = 14.sp,
            color = colorResource(
                id = R.color.text_alpha
            )
        )
        Text(
            text = value,
            fontWeight = FontWeight.Medium,
            fontSize = 16.sp,
            color = colorResource(
                id = R.color.text_color
            )
        )
    }
}

@Composable
fun PercentCard(currency: CurrencyDto) {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(15.dp),
        backgroundColor = colorResource(id = R.color.item_price_card),
        elevation = 0.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp), verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    modifier = Modifier
                        .padding(bottom = 5.dp),
                    text = "1 Hour",
                    fontWeight = FontWeight.Medium,
                    fontSize = 15.sp,
                    color = colorResource(
                        id = R.color.text_color
                    )
                )
                Text(
                    text = "${String.format("%.2f",currency.percent_change_1h)}%",
                    fontWeight = FontWeight.Medium,
                    fontSize = 16.sp,
                    color = if (currency.percent_change_1h >= 0) colorResource(
                        id = R.color.green
                    ) else colorResource(
                        id = R.color.red
                    )
                )
            }
            Column(
                modifier = Modifier
                    .weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    modifier = Modifier
                        .padding(bottom = 5.dp),
                    text = "24 Hour",
                    fontWeight = FontWeight.Medium,
                    fontSize = 15.sp,
                    color = colorResource(
                        id = R.color.text_color
                    )
                )
                Text(
                    text = "${String.format("%.2f", currency.percent_change_24h)}%",
                    fontWeight = FontWeight.Medium,
                    fontSize = 16.sp,
                    color = if (currency.percent_change_1h >= 0) colorResource(
                        id = R.color.green
                    ) else colorResource(
                        id = R.color.red
                    )
                )
            }
            Column(
                modifier = Modifier
                    .weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    modifier = Modifier
                        .padding(bottom = 5.dp),
                    text = "7 Days",
                    fontWeight = FontWeight.Medium,
                    fontSize = 15.sp,
                    color = colorResource(
                        id = R.color.text_color
                    )
                )
                Text(
                    text = "${String.format("%.2f", currency.percent_change_7d)}%",
                    fontWeight = FontWeight.Medium,
                    fontSize = 16.sp,
                    color = if (currency.percent_change_1h >= 0) colorResource(
                        id = R.color.green
                    ) else colorResource(
                        id = R.color.red
                    )
                )
            }
        }
    }
}