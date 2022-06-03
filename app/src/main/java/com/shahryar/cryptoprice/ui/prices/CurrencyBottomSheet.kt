package com.shahryar.cryptoprice.ui.prices

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.shahryar.cryptoprice.R
import com.shahryar.shared.data.model.Currency
import com.shahryar.shared.data.model.CurrencyDto

@Composable
fun CurrencyBottomSheetView(currency: Currency) {
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
                text = "$${currency.price}",
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
            text = currency.lastUpdated,
            fontWeight = FontWeight.Light,
            fontSize = 13.sp,
            color = colorResource(
                id = R.color.text_alpha
            )
        )
    }
}

@Composable
fun OverView(currency: Currency) {
    Column(
        modifier = Modifier
            .padding(start = 15.dp, end = 15.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row {
            Column(modifier = Modifier
                .weight(1f)) {
                ItemOverview(label = stringResource(R.string.total_supply), value = "$${currency.totalSupply}")
            }
            Spacer(modifier = Modifier.weight(0.2f))
            Column(modifier = Modifier.weight(1f)) {
                ItemOverview(
                    label = "Circulating supply",
                    value = "$${currency.circulatingSupply}"
                )
            }
        }
        Spacer(modifier = Modifier.height(20.dp))
        Row {
            Column(modifier = Modifier.weight(1f)) {
                ItemOverview(label = stringResource(R.string.max_supply), value = "$${currency.maxSupply}")
            }
            Spacer(modifier = Modifier.weight(0.2f))
            Column(modifier = Modifier.weight(1f)) {
                ItemOverview(label = stringResource(R.string.hour_volume_24), value = "$${currency.volume24h}")
            }
        }
        Spacer(modifier = Modifier.height(20.dp))
        Row {
            Column(modifier = Modifier.weight(1f)) {
                ItemOverview(label = stringResource(id = R.string.market_cap), value = "$${currency.marketCap}")
            }
            Spacer(modifier = Modifier.weight(0.2f))
            Column(modifier = Modifier.weight(1f)) {
                ItemOverview(label = stringResource(R.string.cmc_rank), value = "${currency.cmcRank}")
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
fun PercentCard(currency: Currency) {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(15.dp),
        backgroundColor = MaterialTheme.colors.primaryVariant,
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
                    text = stringResource(R.string.hour_1),
                    fontWeight = FontWeight.Medium,
                    fontSize = 15.sp,
                    color = colorResource(
                        id = R.color.text_color
                    )
                )
                Text(
                    text = "${currency.percentChange1h}%",
                    fontWeight = FontWeight.Medium,
                    fontSize = 16.sp,
                    color = if (currency.percentChange1h.toDouble() >= 0) colorResource(
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
                    text = stringResource(R.string.hour_24),
                    fontWeight = FontWeight.Medium,
                    fontSize = 15.sp,
                    color = colorResource(
                        id = R.color.text_color
                    )
                )
                Text(
                    text = "${currency.percentChange24h}%",
                    fontWeight = FontWeight.Medium,
                    fontSize = 16.sp,
                    color = if (currency.percentChange24h.toDouble() >= 0) colorResource(
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
                    text = stringResource(id = R.string.day_7),
                    fontWeight = FontWeight.Medium,
                    fontSize = 15.sp,
                    color = colorResource(
                        id = R.color.text_color
                    )
                )
                Text(
                    text = "${currency.percentChange7d}%",
                    fontWeight = FontWeight.Medium,
                    fontSize = 16.sp,
                    color = if (currency.percentChange7d.toDouble() >= 0) colorResource(
                        id = R.color.green
                    ) else colorResource(
                        id = R.color.red
                    )
                )
            }
        }
    }
}