SELECT *
FROM sb_deposit.free_cash_aud
WHERE account_id = :account_id
  AND change_date >= :from_date
  AND change_date <= :to_date