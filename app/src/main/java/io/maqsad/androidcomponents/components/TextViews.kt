package io.maqsad.androidcomponents.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.ZeroCornerSize
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.TextFieldDefaults.indicatorLine
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.*
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.*
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.maqsad.androidcomponents.R
import io.maqsad.androidcomponents.ui.theme.ColorPrimary
import io.maqsad.androidcomponents.ui.theme.DarkGreyColor
import io.maqsad.androidcomponents.ui.theme.PrimaryTextColor
import io.maqsad.androidcomponents.ui.theme.TertiaryColor
import io.maqsad.androidcomponents.ui.theme.fonts

@Composable
fun PrimaryRegularText(
    text: String,
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null,
    fontSize: TextUnit = 11.sp, // 11.sp = Small, 13.sp = Medium, 15.sp = Large
    color: Color = PrimaryTextColor,
    fontWeight: FontWeight = FontWeight.Normal,
    fontStyle: FontStyle? = null,
    fontFamily: FontFamily? = null,
    letterSpacing: TextUnit = TextUnit.Unspecified,
    textDecoration: TextDecoration? = null,
    textAlign: TextAlign? = null,
    lineHeight: TextUnit = TextUnit.Unspecified,
    overflow: TextOverflow = TextOverflow.Clip,
    softWrap: Boolean = true,
    maxLines: Int = Int.MAX_VALUE,
    onTextLayout: (TextLayoutResult) -> Unit = {},
    style: TextStyle = TextStyle(
        fontFamily = fonts,
        fontWeight = fontWeight,
        fontSize = fontSize,
        textDecoration = textDecoration
    )
) {
//    val enabled = remember { mutableStateOf(true) }
    Text(text,
        if (onClick != null) modifier.clickable {
//                enabled.value = false
            onClick()
        } else modifier,
        color,
        fontSize,
        fontStyle,
        fontWeight,
        fontFamily,
        letterSpacing,
        textDecoration,
        textAlign,
        lineHeight,
        overflow,
        softWrap,
        maxLines,
        onTextLayout,
        style)
}

@Composable
fun PrimaryRegularTextAnnotated(
    text: AnnotatedString,
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null,
    fontSize: TextUnit = 11.sp, // 11.sp = Small, 13.sp = Medium, 15.sp = Large
    color: Color = PrimaryTextColor,
    fontWeight: FontWeight = FontWeight.Normal,
    fontStyle: FontStyle? = null,
    fontFamily: FontFamily? = null,
    letterSpacing: TextUnit = TextUnit.Unspecified,
    textDecoration: TextDecoration? = null,
    textAlign: TextAlign? = null,
    lineHeight: TextUnit = TextUnit.Unspecified,
    overflow: TextOverflow = TextOverflow.Clip,
    softWrap: Boolean = true,
    maxLines: Int = Int.MAX_VALUE,
    onTextLayout: (TextLayoutResult) -> Unit = {},
    style: TextStyle = TextStyle(
        fontFamily = fonts,
        fontWeight = fontWeight,
        fontSize = fontSize,
        textDecoration = textDecoration
    )
) {
    val enabled = remember { mutableStateOf(true) }
    Text(text = text,
        modifier = if (onClick != null) modifier.clickable(enabled = enabled.value) {
            enabled.value = false
            onClick()
        } else modifier,
        color = color,
        fontSize = fontSize,
        fontStyle = fontStyle,
        fontWeight = fontWeight,
        fontFamily = fontFamily,
        letterSpacing = letterSpacing,
        textDecoration = textDecoration,
        textAlign = textAlign,
        lineHeight = lineHeight,
        overflow = overflow,
        softWrap = softWrap,
        maxLines = maxLines,
        inlineContent = mapOf(),
        onTextLayout = onTextLayout,
        style = style)
}

@Composable
fun PrimaryMediumText(
    text: String,
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null,
    fontSize: TextUnit = 11.sp, // 12.sp = Small, 14.sp = Medium, 16.sp = Large
    color: Color = PrimaryTextColor,
    fontWeight: FontWeight = FontWeight.Medium,
    fontStyle: FontStyle? = null,
    fontFamily: FontFamily? = null,
    letterSpacing: TextUnit = TextUnit.Unspecified,
    textDecoration: TextDecoration? = null,
    textAlign: TextAlign? = null,
    lineHeight: TextUnit = TextUnit.Unspecified,
    overflow: TextOverflow = TextOverflow.Clip,
    softWrap: Boolean = true,
    maxLines: Int = Int.MAX_VALUE,
    onTextLayout: (TextLayoutResult) -> Unit = {},
    style: TextStyle = TextStyle(
        fontFamily = fonts,
        fontWeight = fontWeight,
        fontSize = fontSize,
        platformStyle = PlatformTextStyle(includeFontPadding = false)
    )
) {
    val enabled = remember { mutableStateOf(true) }

    Text(text,
        if (onClick != null) modifier.clickable(enabled = enabled.value) {
//                enabled.value = false
            onClick()
        } else modifier,
        color,
        fontSize,
        fontStyle,
        fontWeight,
        fontFamily,
        letterSpacing,
        textDecoration,
        textAlign,
        lineHeight,
        overflow,
        softWrap,
        maxLines,
        onTextLayout,
        style)
}

@Composable
fun PrimarySemiBoldText(
    text: String,
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null,
    fontSize: TextUnit = 11.sp, // 12.sp = Small, 14.sp = Medium, 16.sp = Large
    color: Color = PrimaryTextColor,
    fontWeight: FontWeight = FontWeight.SemiBold,
    fontStyle: FontStyle? = null,
    fontFamily: FontFamily? = null,
    letterSpacing: TextUnit = TextUnit.Unspecified,
    textDecoration: TextDecoration? = null,
    textAlign: TextAlign? = null,
    lineHeight: TextUnit = TextUnit.Unspecified,
    overflow: TextOverflow = TextOverflow.Clip,
    softWrap: Boolean = true,
    maxLines: Int = Int.MAX_VALUE,
    onTextLayout: (TextLayoutResult) -> Unit = {},
    style: TextStyle = TextStyle(
        fontFamily = fonts, fontWeight = fontWeight, fontSize = fontSize
    )
) {
    val enabled = remember { mutableStateOf(true) }

    Text(text,
        if (onClick != null) modifier.clickable(enabled = enabled.value) {
            enabled.value = false
            onClick()
        } else modifier,
        color,
        fontSize,
        fontStyle,
        fontWeight,
        fontFamily,
        letterSpacing,
        textDecoration,
        textAlign,
        lineHeight,
        overflow,
        softWrap,
        maxLines,
        onTextLayout,
        style)
}

@Composable
fun PrimaryBoldText(
    text: String,
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null,
    fontSize: TextUnit = 11.sp, // 12.sp = Small, 14.sp = Medium, 16.sp = Large
    color: Color = PrimaryTextColor,
    fontWeight: FontWeight = FontWeight.Bold,
    fontStyle: FontStyle? = null,
    fontFamily: FontFamily? = null,
    letterSpacing: TextUnit = TextUnit.Unspecified,
    textDecoration: TextDecoration? = null,
    textAlign: TextAlign? = null,
    lineHeight: TextUnit = TextUnit.Unspecified,
    overflow: TextOverflow = TextOverflow.Clip,
    softWrap: Boolean = true,
    maxLines: Int = Int.MAX_VALUE,
    onTextLayout: (TextLayoutResult) -> Unit = {},
    style: TextStyle = TextStyle(
        fontFamily = fonts, fontWeight = fontWeight, fontSize = fontSize
    )
) {
    Text(
        text,
        modifier,
        color,
        fontSize,
        fontStyle,
        fontWeight,
        fontFamily,
        letterSpacing,
        textDecoration,
        textAlign,
        lineHeight,
        overflow,
        softWrap,
        maxLines,
        onTextLayout,
        style
    )
}

@Composable
fun PrimaryTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    textStyle: TextStyle = TextStyle(
        fontFamily = fonts, fontWeight = FontWeight.Normal, fontSize = 12.sp
    ),
    backgroundColor: Color = Color.White,
    verticalAlignment: Alignment.Vertical = Alignment.CenterVertically,
    label: @Composable (() -> Unit)? = null,
    placeholder: @Composable (() -> Unit)? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    isError: Boolean = false,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Sentences),
    keyboardActions: KeyboardActions = KeyboardActions(),
    singleLine: Boolean = false,
    maxLines: Int = Int.MAX_VALUE,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    shape: Shape = MaterialTheme.shapes.small.copy(
        bottomEnd = ZeroCornerSize,
        bottomStart = ZeroCornerSize
    ),
    colors: TextFieldColors = TextFieldDefaults.textFieldColors(
        textColor = DarkGreyColor,
        disabledTextColor = Color.Transparent,
        backgroundColor = backgroundColor,
        focusedIndicatorColor = Color.Transparent,
        unfocusedIndicatorColor = Color.Transparent,
        disabledIndicatorColor = Color.Transparent
    )
) {
    // If color is not provided via the text style, use content color as a default
    val textColor = textStyle.color.takeOrElse {
        colors.textColor(enabled).value
    }
    val mergedTextStyle = textStyle.merge(TextStyle(color = textColor))

    @OptIn(ExperimentalMaterialApi::class) (BasicTextField(value = value,
        modifier = modifier
            .background(colors.backgroundColor(enabled).value, shape)
            .indicatorLine(enabled, isError, interactionSource, colors)
            .defaultMinSize(
                minWidth = TextFieldDefaults.MinWidth, minHeight = TextFieldDefaults.MinHeight
            ),
        onValueChange = onValueChange,
        enabled = enabled,
        readOnly = readOnly,
        textStyle = mergedTextStyle,
        cursorBrush = SolidColor(if (isError) colors.cursorColor(true).value else ColorPrimary),
        visualTransformation = visualTransformation,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        interactionSource = interactionSource,
        singleLine = singleLine,
        maxLines = maxLines,
        decorationBox = @Composable { innerTextField ->
            // places leading icon, text field with label and placeholder, trailing icon
//            TextFieldDefaults.TextFieldDecorationBox(
//                value = value,
//                visualTransformation = visualTransformation,
//                innerTextField = innerTextField,
//                placeholder = placeholder,
//                label = label,
//                leadingIcon = leadingIcon,
//                trailingIcon = trailingIcon,
//                singleLine = singleLine,
//                enabled = enabled,
//                isError = isError,
//                interactionSource = interactionSource,
//                colors = colors
//            )
            Row(modifier = modifier, verticalAlignment = verticalAlignment) {
                if (leadingIcon != null) leadingIcon()
                Box(Modifier.weight(1f)) {
                    if (value.isEmpty()) {
                        placeholder?.invoke()
                    }
                    innerTextField()
                }
                if (trailingIcon != null) trailingIcon()
            }
        }))
}

@Composable
fun DeepLinkText(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    isTestAccount: Boolean = true
) {
    if (!isTestAccount) return
    PrimaryMediumText(
        modifier = modifier
            .padding(10.dp)
            .clickable(onClick = onClick),
        text = stringResource(id = R.string.generateLink),
        fontSize = 13.sp,
        color = TertiaryColor,
        textDecoration = TextDecoration.Underline
    )
}

@Composable
fun EmptyComposable() = Unit

@Composable
fun BulletText(modifier: Modifier = Modifier, text: String) {
    Row(modifier = modifier) {
        Image(
            modifier = Modifier.padding(top = 8.dp),
            painter = painterResource(id = R.drawable.ic_ds_bullet),
            contentDescription = null
        )
        Spacer(modifier = Modifier.width(8.dp))
        PrimaryRegularText(
            text = text, fontSize = 14.sp, color = PrimaryTextColor
        )
    }
}

@Composable
fun BulletText(
    modifier: Modifier = Modifier,
    text: AnnotatedString,
    fontSize: TextUnit,
    color: Color,
    tint: Color
) {
    Row(modifier = modifier) {
        Image(
            modifier = Modifier.padding(top = 8.dp),
            painter = painterResource(id = R.drawable.ic_ds_bullet),
            contentDescription = null,
            colorFilter = ColorFilter.tint(color = tint)
        )
        Spacer(modifier = Modifier.width(8.dp))
        PrimaryRegularTextAnnotated(
            text = text, fontSize = fontSize, color = color
        )
    }
}

@Composable
fun BulletText(
    modifier: Modifier = Modifier,
    text: String,
    fontSize: TextUnit,
    color: Color,
    tint: Color,
    paddingValues: PaddingValues = PaddingValues(top = 8.dp),
    verticalAlignment: Alignment.Vertical = Alignment.CenterVertically,
) {
    Row(modifier = modifier, verticalAlignment = verticalAlignment) {
        Image(
            modifier = Modifier.padding(paddingValues),
            painter = painterResource(id = R.drawable.ic_ds_bullet),
            contentDescription = null,
            colorFilter = ColorFilter.tint(color = tint)
        )
        Spacer(modifier = Modifier.width(8.dp))
        PrimaryRegularText(
            text = text, fontSize = fontSize, color = color
        )
    }
}