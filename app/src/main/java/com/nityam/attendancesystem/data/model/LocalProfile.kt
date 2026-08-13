package com.nityam.attendancesystem.data.model

import com.nityam.attendancesystem.common.Gender

/**
 * NOTE: the backend currently has no GET /me or /profile endpoint - only
 * /api/v1/auth/register and /api/v1/auth/login exist. Until one is added, this is
 * populated locally:
 *  - fully, from the fields the user submitted on the Register screen
 *  - partially (email only), when a user just logs in on a device that never registered here
 *
 * Once a real profile endpoint exists, replace ProfileRepository's local-DataStore read with
 * a network call and this model can be dropped in favor of a proper response DTO - the
 * Profile screen/ViewModel already consume it as an interface-shaped state, so that swap
 * shouldn't require UI changes.
 */
data class LocalProfile(
    val firstName: String? = null,
    val lastName: String? = null,
    val email: String? = null,
    val phone: String? = null,
    val dob: String? = null,
    val gender: Gender? = null
) {
    val isEmpty: Boolean
        get() = firstName == null && lastName == null && email == null &&
            phone == null && dob == null && gender == null
}
