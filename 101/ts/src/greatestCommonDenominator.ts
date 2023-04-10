export const greatestCommonDenominator = (a: number, b: number): number => {
  const remainder = a % b;

  if (remainder === 0) {
    return b;
  } else {
    return greatestCommonDenominator(b, remainder);
  }
};
